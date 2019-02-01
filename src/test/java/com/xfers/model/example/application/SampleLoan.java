package com.xfers.model.example.application;

import com.xfers.Xfers;
import com.xfers.model.channeling.loan.Collateral;
import com.xfers.model.channeling.loan.Customer;
import com.xfers.model.channeling.loan.Detail;
import com.xfers.model.channeling.loan.Installment;
import com.xfers.model.channeling.loan.Loan;
import com.xfers.model.channeling.loan.Repayment;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleLoan {
    // Can only be used once per create request
    private static String refno = "";

    public static void main(String[] args) {
        Xfers.setIDSandbox();
        String userApiToken = "";

        exampleCreateLoan(userApiToken);
        Loan loan = exampleGetLoan("loan_xx", userApiToken);
        List<Repayment> repayments = exampleGetRepayments(loan, userApiToken);
        Repayment repayment = exampleGetRepayment(loan, "loan_repayment_xx", userApiToken);
    }

    private static void exampleCreateLoan(String userApiToken) {
        Loan loan = new Loan()
            .customer(exampleCustomer())
            .collateral(exampleCollateral())
            .detail(exampleLoanDetail())
            .installment(exampleInstallment());

        try {
            String result = loan.create(userApiToken);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static Loan exampleGetLoan(String loanID, String userApiToken) {
        try {
            return Loan.getLoan(loanID, userApiToken);
        } catch (Exception e) {
            return null;
        }
    }

    private static void exampleCreateRepayment(Loan loan, String userApiToken) {
        Map<String, Object> params = new HashMap<String,Object>();

        try {
            String result = loan.createRepayment(params, userApiToken);
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
            return null;
        }
    }

    private static Date parseDate(String date) {
        try {
            return DateFormat.getDateInstance().parse(date);
        } catch (Exception e) {
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
