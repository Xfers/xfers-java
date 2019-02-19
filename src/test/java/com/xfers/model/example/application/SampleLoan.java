package com.xfers.model.example.application;

import com.xfers.Xfers;
import com.xfers.model.BankAccount;
import com.xfers.model.Connect;
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
import com.xfers.model.channeling.loan.response.CreateRepaymentResponse;
import com.xfers.model.channeling.loan.response.GetDisbursementResponse;
import com.xfers.model.channeling.loan.response.ListDisbursementResponse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleLoan {
    // These values must be unique per create request
    private static String refno = ""; // for creating new loan
    private static String disbursementIdempotencyID = ""; // for creating new disbursement
    private static String repaymentIdempotencyID = ""; // for creating new loan repayment

    public static void main(String[] args) {
        Xfers.setIDSandbox();

        String xfersAppApiKey = "";
        String xfersAppSecretKey = "";
        String phoneNumber = "";

        /*********************** USER KYC FLOW ***********************/

        String userApiToken = exampleSignUp(xfersAppApiKey, xfersAppSecretKey, phoneNumber);

        exampleKycSubmission(userApiToken);
        // callback at user_verification_status_updated
        // exampleCheckKycStatus(userApiToken)

        // Mock verification can only be used in sandbox.
        exampleMockVerification(userApiToken, 3);

        String bankAccountID = exampleAddBankAccount(userApiToken);
        // exampleGetBankAccount(userApiToken)
        // exampleListBankAccounts(userApiToken)

        /*********************** LOAN DISBURSEMENT FLOW ***********************/

        String loanID = exampleCreateLoan(userApiToken);
        // callback at loan_request_approved
        Loan loan = exampleGetLoan(loanID, userApiToken);

        Disbursement result = exampleCreateDisbursement(loan, xfersAppApiKey, bankAccountID, userApiToken);
        // callback at withdrawal_completed
        List<Disbursement> disbursements = exampleGetAllDisbursements(loan, userApiToken);
        Disbursement disbursement = exampleGetDisbursement(loan, result.getId(), userApiToken);

        // Wait for a period of time between disbursement creation and mocking disbursement status.
        try { Thread.sleep(10000); } catch(InterruptedException ex) { }

        exampleMockDisbursementStatus(result, userApiToken);

        // Wait for a period of time between after disbursement completed before making report.
        try { Thread.sleep(3000); } catch(InterruptedException ex) { }

        exampleCreateDisbursementReport(loan, userApiToken);
        // callback at loan_disbursement_report_completed
        loan = exampleGetLoan(loanID, userApiToken);

        /*********************** REPAYMENT FLOW ***********************/

        String repaymentID = exampleCreateRepayment(loan, userApiToken);
        // callback at loan_repayment_created
        List<Repayment> repayments = exampleGetAllRepayments(loan, userApiToken);
        Repayment repayment = exampleGetRepayment(loan, repaymentID, userApiToken);

        /*********************** RECONCILIATIONS ***********************/

        exampleCallOutstandingLoans(xfersAppApiKey);
        // callback at loan_reconciliation_requested

        exampleCallOutstandingRepayments(xfersAppApiKey);
        // callback at loan_repayment_reconciliations_requested
    }


    /**
     * This function is to create a new user and returns its user api token which
     * will be used to identify the user onward
     */
    private static String exampleSignUp(String xfersAppApiKey, String xfersAppSecretKey, String phoneNumber) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone_no", phoneNumber);
        try {
            Response response = Connect.privateAuthorize(params, xfersAppApiKey, xfersAppSecretKey);
            String userApiToken = response.getUserApiToken();
            System.out.println("User API Token: " + userApiToken);
            return userApiToken;
        } catch (Exception e) {
            System.out.println("Sign up error: " + e);
            return null;
        }
    }

    /**
     * Submit KYC data to create Xfers-Sobatku Wallet.
     * If Approved, a Xfers-Sobatku Wallet will be created for that user.
     * There will be callback for approval or rejection
     */
    private static void exampleKycSubmission(String userApiToken) {
        Map<String, Object> updateParams = new HashMap<String, Object>();

        // Mandatory basic fields
        updateParams.put("id_front", "http://gambar_ktp.jpg"); // Photo of person's KTP
        updateParams.put("selfie_2id", "http://gambar_selfie.jpg"); // Selfie or screen capture of the liveness test
        updateParams.put("mother_maiden_name", "Jane Doe");

        // Mandatory KTP fields
        updateParams.put("state", "DKI Jakarta"); // Taken from most top line of KTP, without the word "PROVINSI"
        updateParams.put("city", "Jakarta Selatan"); // Taken from second line from top of KTP
        updateParams.put("identity_no", "1234567890120001"); // Taken from KTP "NIK" field
        updateParams.put("full_name", "John Doe"); // Taken from KTP "Nama" field
        updateParams.put("place_of_birth", "Bandung"); // Taken from KTP "Tempat" field
        updateParams.put("date_of_birth", "2000-02-29"); // Taken from KTP "Tgl Lahir" field
        updateParams.put("gender", "male"); // Taken from KTP "Jenis Kelamin" field; Options are "male" for LAKI-LAKI or "female" for PEREMPUAN only
        updateParams.put("blood_type", "-"); // Taken from KTP "Gol. Darah" field; Options are "O", "A", "B", "AB", or "-" only
        updateParams.put("address_line_1", "Jl. Razhunna Seith"); // Taken from KTP "Alamat" field; Also refer to optional address_line_2 field below
        updateParams.put("rt_rw", "005/001"); // Taken from KTP "RT/RW" field, as is
        updateParams.put("administrative_village", "Setiabudi"); // Taken from KTP "Kel/Desa" field
        updateParams.put("district", "Setiabudi"); // Taken from KTP "Kecamatan" field
        updateParams.put("religion", "Budha"); // Taken from KTP "Agama" field; Options are "Islam", "Katholik", "Kristen Protestan", "Hindu", "Budha", "Kong Hu Cu", or "Aliran Kepercayaan" only
        updateParams.put("marital_status", "Belum Kawin"); // Taken from KTP "Status Perkawinan" field; Options are "Belum Kawin", "Kawin", "Janda", or "Duda" only
        updateParams.put("occupation", "Pelajar/Mahasiswa"); // Taken from KTP "Pekerjaan" field, as is
        updateParams.put("nationality", "Indonesian"); // Should always be "Indonesian"
        updateParams.put("nric_issue_date", "2017-03-01"); // Taken from the date below KTP photo

        // Optional KTP field
        updateParams.put("address_line_2", "Apartemen Saiber Dhua lantai 123"); // Taken from KTP "Alamat" field if address is more than one line

        try {
            User.update(updateParams, userApiToken);
            System.out.println("KYC data submission success!");
        } catch(Exception e) {
            System.out.println("Kyc data submission error: " + e);
        }
    }

    /**
     * This function creates a new bank account for corresponding user.
     * This function also gets the detected name of the user from the bank system and return it to you synchronously
     * The name provided by you and the bank should be similar (doesn't require exact match)
     * account_holder_name must be "PROD ONLY" for sandbox.
     */
    private static String exampleAddBankAccount(String userApiToken) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account_no", "123456789");
        params.put("bank", "BCA");
        params.put("account_holder_name", "PROD ONLY");

        try {
            List<BankAccount> bankAccounts = BankAccount.add(params, userApiToken);
            BankAccount newAccount = bankAccounts.get(bankAccounts.size() - 1);
            String bankID = newAccount.getId();
            System.out.println("Bank ID: " + bankID);
            return bankID;
        } catch (Exception e) {
            System.out.println("Add bank account error: " + e);
            return "";
        }
    }

    /**
     * This function is to immediately verify the user specified.
     * In production, the verification is done manually by Xfers KYC team.
     * Can only be used in sandbox
     */
    private static void exampleMockVerification(String userApiToken, int retries) {
        if (0 >= retries) {
            System.out.println("Mock verification error!");
            return;
        }
        try {
            User.mockVerify(userApiToken);
            System.out.println("Mock verification success!");
        } catch (Exception e) {
            exampleMockVerification(userApiToken, retries - 1);
        }
    }

    /**
     * After creating a loan, Partner Bank will do some background checking on the user whether the user
     * will be eligible for the loan.
     * After being approved, should create a disbursement to that user.
     */
    private static String exampleCreateLoan(String userApiToken) {
        Loan loan = new Loan()
            .customer(exampleCustomer())
            .collateral(exampleCollateral())
            .detail(exampleLoanDetail())
            .installment(exampleInstallment());
        try {
            String result = loan.create(userApiToken);
            String loanID = Loan.fromJSON(result).getId();
            System.out.println("Loan ID: " + loanID);
            return loanID;
        } catch (Exception e) {
            System.out.println("Create loan error: " + e);
            return null;
        }
    }

    /**
     *  Gets a loan.
     *  The most important parameter here is `status`
     *  (refer to https://documenter.getpostman.com/view/5775523/RznLFvgh#89762ab3-d4f2-491d-beba-fe90ddf3afd2)
     */
    private static Loan exampleGetLoan(String loanID, String userApiToken) {
        try {
            return Loan.getLoan(loanID, userApiToken);
        } catch (Exception e) {
            System.out.println("Get loan error: " + e);
            return null;
        }
    }

    /**
     *  Sends money from your managed account into the user's bank account
     */
    private static Disbursement exampleCreateDisbursement(Loan loan, String xfersAppApiKey, String bankAccountID, String userApiToken) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", "10345.67");
        params.put("user_api_token", userApiToken);
        params.put("idempotency_id", disbursementIdempotencyID);
        params.put("bank_account_id", bankAccountID);

        try {
            CreateDisbursementResponse response = loan.createDisbursement(params, xfersAppApiKey);
            Disbursement disbursement = response.getDisbursement();
            System.out.println("Disbursement ID: " + disbursement.getId());
            System.out.println("Disbursement Idempotency ID: " + disbursement.getIdempotencyID());
            return response.getDisbursement();
        } catch (Exception e) {
            System.out.println("Create disbursement error: " + e);
            return null;
        }
    }

    /**
     *  List all disbursements that is done for a specific loan
     */
    private static List<Disbursement> exampleGetAllDisbursements(Loan loan, String userApiToken) {
        try {
            ListDisbursementResponse response = loan.getAllDisbursements(userApiToken);
            return response.getDisbursements();
        } catch (Exception e) {
            System.out.println("Get all disbursements error: " + e);
            return null;
        }
    }

    /**
     *  Get Status of One Disbursement
     */
    private static Disbursement exampleGetDisbursement(Loan loan, String contractID, String userApiToken) {
        try {
            GetDisbursementResponse response = loan.getDisbursement(contractID, userApiToken);
            return response.getDisbursement();
        } catch (Exception e) {
            System.out.println("Get disbursement error: " + e);
            return null;
        }
    }

    /**
     * This function is to mock whether a status of a disbursement is `completed` or `failed`
     * Can only be used in sandbox
     */
    private static void exampleMockDisbursementStatus(Disbursement disbursement, String userApiToken) {
        try {
            disbursement.mockStatusChange("completed", userApiToken);
            System.out.println("Mock disbursement status success!");
        } catch (Exception e) {
            System.out.println("Mock disbursement status error: " + e);
        }
    }

    /**
     *  Notify Partner Bank that a disbursement is completed to complete the loan disbursement process
     */
    private static void exampleCreateDisbursementReport(Loan loan, String userApiToken) {
        try {
            loan.createDisbursementReport(userApiToken);
            System.out.println("Create disbursement report success!");
        } catch (Exception e) {
            System.out.println("Create disbursement report error: " + e);
        }
    }

    /**
     *  Create a repayment notification to Partnering Bank to tell that a user has done a repayment to the loan
     */
    private static String exampleCreateRepayment(Loan loan, String userApiToken) {
        BigDecimal amount = new BigDecimal("10000.0");
        BigDecimal collectionFee = new BigDecimal("0"); // Should be zero

        try {
            CreateRepaymentResponse response = loan.createRepayment(amount, collectionFee, repaymentIdempotencyID, userApiToken);
            String repaymentID = response.getLoanRepaymentID();
            System.out.println("Repayment ID: " + repaymentID);
            return repaymentID;
        } catch (Exception e) {
            System.out.println("Create repayment error: " + e);
            return null;
        }
    }

    /**
     *  List all repayments that is done for a specific loan
     */
    private static List<Repayment> exampleGetAllRepayments(Loan loan, String userApiToken) {
        try {
            return loan.getAllRepayments(userApiToken);
        } catch (Exception e) {
            System.out.println("Get all repayments error: " + e);
            return null;
        }
    }

    /**
     *  Get Status of One Disbursement
     */
    private static Repayment exampleGetRepayment(Loan loan, String repaymentID, String userApiToken) {
        try {
            return loan.getRepayment(repaymentID, userApiToken);
        } catch (Exception e) {
            System.out.println("Get repayment error: " + e);
            return null;
        }
    }

    /**
     *  Get Outstanding Loans From Partnering Bank.
     *  This will be used for reconciliation purpose
     */
    private static void exampleCallOutstandingLoans(String xfersAppApiKey) {
        try {
            Loan.outstandingLoans(1, 100000, xfersAppApiKey);
            System.out.println("Call outstanding loans success!");
        } catch (Exception e) {
            System.out.println("Call outstanding loans error: " + e);
        }
    }

    /**
     *  Get Outstanding Repayment From Partnering Bank of a given date.
     *  This will be used for reconciliation purpose
     */
    private static void exampleCallOutstandingRepayments(String xfersAppApiKey) {
        try {
            Loan.outstandingLoanRepayments(parseDate("2019-1-21"), 1, 100000, xfersAppApiKey);
            System.out.println("Call outstanding repayments success!");
        } catch (Exception e) {
            System.out.println("Call outstanding repayments error: " + e);
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
     * Without the following fields, the loan creation function will throw an error.
     */
    private static Customer exampleCustomer() {
        return new Customer()
            .fullname("John Doe")
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
            .custdati("0102")
            .idtype(1)
            .idnumber("3761493104850099")
            .idexpired(parseDate("2050-01-01"))
            .gender(1)
            .maritalstatus(0)
            .birthdate(parseDate("1999-01-01"))
            .birthplace("kotalahir")
            .birthdati("birdati")
            .economycode("011110")
            .debiturcode("907")
            .mmn("namaibu")
            .homestatus(1)
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
            .custdatihome("0102")
            .relativesname("namakerabat")
            .custaddressrel("almatkerabat")
            .jobid("002")
            .jobtitleid("50")
            .countryid("ID")
            .branchcode("000")
            .lasteducation("0100");
    }

    /**
     * This example contains mandatory fields.
     * Without the following fields, the loan creation function will throw an error.
     */
    private static Collateral exampleCollateral() {
        return new Collateral()
            .productcode("C304")
            .merkcode("mrk")
            .modelcode("mdl")
            .collateralno("collno939854")
            .collateraladdress("alamatkolateral")
            .collateralcityid("0102")
            .collateralname("namakolateral")
            .engineno("nomormesin")
            .chassisno("nomorcasis")
            .collateralyear(2018)
            .buildyear(1999)
            .condition(1)
            .color("black and pink")
            .collateralkind("KTA01")
            .collateralpurpose("tujuankolateral")
            .policeno("X 1234 YZ")
            .surveydate(parseDate("2019-02-01"))
            .bindtypecode("99")
            .collateraltypecode("250")
            .collateralvalue(new BigDecimal("100000000"))
            .dealercode("TK");
    }

    /**
     * This example contains mandatory fields.
     * Without the following fields, the loan creation function will throw an error.
     */
    private static Detail exampleLoanDetail() {
        return new Detail()
            .refno(refno)
            .objectvalue(new BigDecimal("10000000000.0"))
            .principaltotal(new BigDecimal("1000000.0"))
            .tenor(1)
            .loantype(0)
            .effectiverate(new BigDecimal("0.25"))
            .installment(new BigDecimal("1000000.0"))
            .firstinstdate(parseDate("2019-03-01"))
            .admfee(new BigDecimal("0.0"))
            .inscode("001")
            .inspremi("0.0")
            .insonloan("0.0")
            .installmenttype(1)
            .branchcode("000")
            .typeofuseid("89")
            .orientationofuseid("9")
            .debiturcatid("10")
            .portfoliocatid("idportfoliocat")
            .credittypeid("20")
            .creditattributeid("20")
            .creditcategoryid("10")
            .fincat("000")
            .installfeeaccount(new BigDecimal("50000.0"));
    }

    /**
     * This example contains mandatory fields.
     * Without the following fields, the loan creation function will throw an error.
     */
    private static Installment exampleInstallment() {
        return new Installment()
            .duedate(parseDate("2019-04-01"))
            .principal(new BigDecimal("1000000.0"))
            .interest(new BigDecimal("10000000.0"))
            .installfee(new BigDecimal("50000.0"))
            .period(6);
    }
}
