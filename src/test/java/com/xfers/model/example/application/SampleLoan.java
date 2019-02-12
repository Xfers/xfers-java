package com.xfers.model.example.application;

import com.xfers.Xfers;
import com.xfers.model.Connect;
import com.xfers.model.Response;
import com.xfers.model.User;
import com.xfers.model.channeling.loan.Collateral;
import com.xfers.model.channeling.loan.Customer;
import com.xfers.model.channeling.loan.Detail;
import com.xfers.model.channeling.loan.Installment;
import com.xfers.model.channeling.loan.Loan;
import com.xfers.model.channeling.loan.Repayment;

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

        exampleMockVerification(userApiToken, 3);

        exampleCreateLoan(userApiToken);
        Loan loan = exampleGetLoan("loan_xx", userApiToken);

        exampleCreateRepayment(loan, userApiToken);
        List<Repayment> repayments = exampleGetRepayments(loan, userApiToken);
        Repayment repayment = exampleGetRepayment(loan, "loan_repayment_xx", userApiToken);
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
            System.out.println(e.getMessage());
            return null;
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

    private static List<Repayment> exampleGetRepayments(Loan loan, String userApiToken) {
        try {
            return loan.getRepayments(userApiToken);
        } catch (Exception e) {
            return null;
        }
    }

    private static Repayment exampleGetRepayment(Loan loan, String repaymentID, String userApiToken) {
        try {
            return loan.getRepayment(repaymentID, userApiToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            System.out.println("Date parse error: " + e.getMessage());
            return new Date();
        }
    }

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
            .branchcode("kantorcabang");
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
