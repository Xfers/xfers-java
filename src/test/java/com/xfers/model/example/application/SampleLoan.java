package com.xfers.model.example.application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xfers.Xfers;
import com.xfers.model.BankAccount;
import com.xfers.model.Connect;
import com.xfers.model.Payout;
import com.xfers.model.Response;
import com.xfers.model.User;
import com.xfers.model.channeling.loan.Collateral;
import com.xfers.model.channeling.loan.Customer;
import com.xfers.model.channeling.loan.Detail;
import com.xfers.model.channeling.loan.Disbursement;
import com.xfers.model.channeling.loan.Installment;
import com.xfers.model.channeling.loan.Loan;
import com.xfers.model.channeling.loan.Repayment;
import com.xfers.model.channeling.loan.response.CreateDisbursementResponse;
import com.xfers.model.channeling.loan.response.GetDisbursementResponse;
import com.xfers.model.channeling.loan.response.ListDisbursementResponse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleLoan {
    // Define once per create request
    private static String refno = "";

    public static void main(String[] args) {
        Xfers.setIDSandbox();

        String xfersAppApiKey = "";
        String xfersAppSecretKey = "";
        String phoneNumber = "";
        String userApiToken = exampleSignUp(xfersAppApiKey, xfersAppSecretKey, phoneNumber);
        System.out.println(userApiToken); // Should save this

        exampleKycSubmission(userApiToken);

        // Mock verification can only be used in sandbox.
        // In production, the verification is done manually by Xfers KYC team.
        exampleMockVerification(userApiToken, 3);
        
        String bankAccountID = exampleAddBankAccount(userApiToken);

        // After create a loan, wait for a period for bank validation.
        // Then, get the loan and create a disbursement.
        exampleCreateLoan(userApiToken);
        Loan loan = exampleGetLoan("loan_xxx", userApiToken);
        exampleCreateDisbursement(loan, xfersAppApiKey, bankAccountID, userApiToken);

        List<Disbursement> disbursements = exampleGetAllDisbursements(loan, userApiToken);
        Disbursement disbursement = exampleGetDisbursement(loan, "contract_xxx", userApiToken);

        // Payout first before create repayment, should have the same amount.
        // Make sure to have the balance. (top up first, manually in production, or via https://sandbox-id.xfers.com in sandbox)
        examplePayout(xfersAppApiKey, userApiToken);
        exampleCreateRepayment(loan, userApiToken);

        List<Repayment> repayments = exampleGetAllRepayments(loan, userApiToken);
        Repayment repayment = exampleGetRepayment(loan, "loan_repayment_xxx", userApiToken);
    }

    /**
     * This function is to create a new user and returns its user api token.
     * To update the information of the user, use User.update(), which is explained elsewhere.
     * For old user, use its corresponding user api token.
     */
    private static String exampleSignUp(String xfersAppApiKey, String xfersAppSecretKey, String phoneNumber) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone_no", phoneNumber);
        try {
            Response response = Connect.privateAuthorize(params, xfersAppApiKey, xfersAppSecretKey);
            return response.getUserApiToken();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * This example contains mandatory fields.
     * Without the following fields, the following may happen:
     *      1. The User.update function throw an error.
     *      2. The corresponding user may be rejected by Xfers KYC team.
     */
    private static void exampleKycSubmission(String userApiToken) {
        Map<String, Object> updateParams = new HashMap<String, Object>();
        updateParams.put("first_name", "Firist");
        updateParams.put("last_name", "Laset");
        updateParams.put("email", "firist@laset.com");
        updateParams.put("identity_no", "1234567890120001"); // KTP number
        updateParams.put("id_front", "http://gambar_ktp.jpg"); // Make sure this is a valid image with public access
        updateParams.put("selfie_2id", "http://gambar_selfie.jpg"); // Make sure this is a valid image with public access

        updateParams.put("date_of_birth", "2000-02-29"); // All date format is yyyy-mm-dd
        updateParams.put("country_of_birth", "Indonesia"); // "id" means Indonesia
        updateParams.put("gender", "male"); // "male" or "female" only
        updateParams.put("address_line_1", "Jl. Razhunna Seith");
        updateParams.put("address_line_2", "Apartemen Saiber Dhua lantai 666");
        updateParams.put("nric_type", "Indonesian");
        updateParams.put("nric_issue_date", "2017-03-01");
        updateParams.put("nationality", "Indonesian");
        updateParams.put("postal_code", "66666");
        updateParams.put("country", "id"); // "id" means Indonesia
        updateParams.put("state", "DKI Jakarta");
        updateParams.put("city", "Jakarta Selatan");
        updateParams.put("mother_maiden_name", "Indonesian");
        updateParams.put("place_of_birth", "Bandung");
        updateParams.put("rt_rw", "005/001"); // The format is RT/RW, with or without leading zero
        updateParams.put("administrative_village", "Setiabudi");
        updateParams.put("district", "Setiabudi");

        try {
            User.update(updateParams, userApiToken);
            System.out.println("KYC data submission success!");
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    private static String exampleAddBankAccount(String userApiToken) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account_no", "123456789");
        params.put("bank", "BCA");
        params.put("account_holder_name", "PROD ONLY");

        try {
            List<BankAccount> bankAccounts = BankAccount.add(params);
            BankAccount newAccount = bankAccounts.get(bankAccounts.size() - 1);
            return newAccount.getId();
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

    /**
     * This function is to immediately verify the user whose api token in parametre.
     * Can't be used in production server and will throws an error.
     */
    private static void exampleMockVerification(String userApiToken, int retries) {
        if (0 >= retries) {
            System.out.println("Unknown error!");
        }
        try {
            User.mockVerify(userApiToken);
            System.out.println("Verification success!");
        } catch (Exception e) {
            exampleMockVerification(userApiToken, retries - 1);
        }
    }

    private static String exampleCreateLoan(String userApiToken) {
        Loan loan = new Loan()
            .customer(exampleCustomer())
            .collateral(exampleCollateral())
            .detail(exampleLoanDetail())
            .installment(exampleInstallment());
        try {
            String result = loan.create(userApiToken);
            String loanID = Loan.fromJSON(result).getId();
            System.out.println(loanID);
            return loanID;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static Loan exampleGetLoan(String loanID, String userApiToken) {
        try {
            return Loan.getLoan(loanID, userApiToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static String exampleCreateDisbursement(Loan loan, String xfersAppApiKey, String bankAccountID, String userApiToken) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", "10345.67");
        params.put("user_api_token", userApiToken);
        params.put("payout_invoice_id", "UN1QUE-D15BUR53M3NT-V4LU3-HE12E"); // Must be unique per disbursement creation.
        params.put("notify_url", "http://localhost:4000/callback");
        params.put("bank_account_id", bankAccountID);

        try {
            CreateDisbursementResponse response = loan.createDisbursement(params, xfersAppApiKey);
            return response.getDisbursement().getId();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static List<Disbursement> exampleGetAllDisbursements(Loan loan, String userApiToken) {
        try {
            ListDisbursementResponse response = loan.getAllDisbursements(userApiToken);
            return response.getDisbursements();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static Disbursement exampleGetDisbursement(Loan loan, String contractID, String userApiToken) {
        try {
            GetDisbursementResponse response = loan.getDisbursement(contractID, userApiToken);
            return response.getDisbursement();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static String examplePayout(String xfersAppApiKey, String userApiToken) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", "10345.67");
        params.put("user_api_token", userApiToken);
        params.put("invoice_id", "UN1QUE-P4Y0UT-V4LU3-HE12E"); // Must be unique per payout creation.
        params.put("descriptions", "For loan");

        try {
            Payout payout = Payout.create(params, xfersAppApiKey);
            return payout.getId();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static void exampleCreateRepayment(Loan loan, String userApiToken) {
        BigDecimal amount = new BigDecimal("10000.0");
        BigDecimal collectionFee = new BigDecimal("25.0");

        try {
            String result = loan.createRepayment(amount, collectionFee, userApiToken);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static List<Repayment> exampleGetAllRepayments(Loan loan, String userApiToken) {
        try {
            return loan.getAllRepayments(userApiToken);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static Repayment exampleGetRepayment(Loan loan, String repaymentID, String userApiToken) {
        try {
            return loan.getRepayment(repaymentID, userApiToken);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            System.out.println("Date parse error: " + e);
            return new Date();
        }
    }

    /**
     * This example contains mandatory fields.
     * Without the following fields, the create function will throw an error.
     * The same with exampleCollateral, exampleLoanDetail, and exampleInstallment.
     */
    private static Customer exampleCustomer() {
        return new Customer()
            .fullname("namalengkapkustomer")
            .custtype(1)
            .custname("namakustomer")
            .custaddress("alamatkustomer")
            .custrt("RT001")
            .custrw("RW002")
            .custkel("kelurahankustomer")
            .custkec("kecamatankustomer")
            .custcity("kotakustomer")
            .custprov("provinsikustomer")
            .custzip("14253")
            .custdati("kusdati")
            .idtype(1)
            .idnumber("3761493104850099")
            .idexpired(parseDate("2050-01-01"))
            .gender(0)
            .maritalstatus(0)
            .birthdate(parseDate("1999-01-01"))
            .birthplace("kotalahir")
            .birthdati("birdati")
            .economycode("superkaya")
            .debiturcode("kodedebitur")
            .mmn("namaibu")
            .homestatus(0)
            .livedsince(parseDate("2015-01-01"))
            .phonearea("+62")
            .phoneno("08123456789")
            .sameidhomeaddr(1)
            .custaddresshome("alamatsekarang")
            .custrthome("RT010")
            .custrwhome("RW011")
            .custkelhome("kelurahansekarang")
            .custkechome("kecamatansekarang")
            .custcityhome("kotasekarang")
            .custprovhome("provinsisekarang")
            .custziphome("89765")
            .custdatihome("kusdatisekarang")
            .relativesname("namadulur")
            .custaddressrel("almatdulur")
            .jobid("kerjaan")
            .jobtitleid("karyawan")
            .countryid("ID")
            .branchcode("kantorcabang")
            .lasteducation("0100");
    }

    private static Collateral exampleCollateral() {
        return new Collateral()
            .productcode("kodeproduk")
            .merkcode("kodemerk")
            .modelcode("kodemodel")
            .collateralno("nomorkolateral")
            .collateraladdress("alamatkolateral")
            .collateralname("namakolateral")
            .engineno("nomormesin")
            .chassisno("nomorcasis")
            .collateralyear(2025)
            .buildyear(1999)
            .condition(1)
            .color("black and pink")
            .collateralkind("jeniskolateral")
            .collateralpurpose("tujuankolateral")
            .policeno("X 1234 YZ")
            .surveydate(parseDate("2019-02-01"))
            .bindtypecode("kode")
            .collateraltypecode("kodetipekolateral")
            .collateralvalue(new BigDecimal("1000000.005"))
            .dealercode("kodediler");
    }

    private static Detail exampleLoanDetail() {
        return new Detail()
            .refno(refno)
            .objectvalue(new BigDecimal("1000000.005"))
            .principaltotal(new BigDecimal("1000000.005"))
            .tenor(1)
            .loantype(1)
            .effectiverate(new BigDecimal("0.25"))
            .installment(new BigDecimal("50000.01"))
            .firstinstdate(parseDate("2019-03-01"))
            .admfee(new BigDecimal("100.25"))
            .inscode("kode ins")
            .inspremi("premi ins")
            .insonloan("ins onloan")
            .installmenttype(1)
            .branchcode("kodecabang")
            .typeofuseid("idtipefuse")
            .orientationofuseid("arahfuseid")
            .debiturcatid("iddebiturcat")
            .portfoliocatid("idportfoliocat")
            .credittypeid("idtipekredit")
            .creditattributeid("idstributkredit")
            .creditcategoryid("idkreditkategori")
            .fincat("fincat")
            .installfeeaccount(new BigDecimal("200.4"));
    }

    private static Installment exampleInstallment() {
        return new Installment()
            .duedate(parseDate("2019-04-01"))
            .principal(new BigDecimal("120000.13"))
            .interest(new BigDecimal("1.25"))
            .installfee(new BigDecimal("130.78"))
            .period(6);
    }
}
