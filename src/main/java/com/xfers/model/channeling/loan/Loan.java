package com.xfers.model.channeling.loan;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loan {
    private static final String loanURL = "/loans";
    private String id;
    private String status;
    private String lastRacErrorReason;
    private String lastSlikErrorReason;
    private String lastWithdrawalOnBehalfErrorReason;
    private String lastDisbursementReportErrorReason;
    @SerializedName("collateral_data") private Collateral collateral;
    @SerializedName("company_management_data") private CompanyManagement companyManagement;
    @SerializedName("customer_data") private Customer customer;
    @SerializedName("loan_data") private Detail detail;
    @SerializedName("installment_data") private Installment installment;

    public Loan collateral(Collateral collateral) {
        this.collateral = collateral;
        return this;
    }

    public Loan companyManagement(CompanyManagement companyManagement) {
        this.companyManagement = companyManagement;
        return this;
    }

    public Loan customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Loan detail(Detail detail) {
        this.detail = detail;
        return this;
    }

    public Loan installment(Installment installment) {
        this.installment = installment;
        return this;
    }

    public static Loan fromJSON(String json) {
        return new Gson().fromJson(json, Loan.class);
    }

    public static Loan getLoan(String id, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = loanURL + "/" + id;
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, userApiToken);
        return fromJSON(response);
    }

    public String create(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        if (null == customer || null == collateral || null == detail || null == installment) {
            return "Customer, Collateral, LoanDetail, and Installment must be present!";
        }

        Map<String, Object> params = new HashMap<String,Object>();
        params.put("customer_data", customer);
        params.put("collateral_data", collateral);
        params.put("loan_data", detail);
        params.put("installment_data", installment);

        if (null != companyManagement) {
            params.put("company_management_data", companyManagement);
        }

        String stringParams = new Gson().toJson(params);

        String url = loanURL;
        String response = new CustomHTTPConnection().post(url, userApiToken, stringParams);
        return response;
    }

    public static String createLoan(Map<String, Object> params, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Object customer_hash = params.get("customer_data");
        if (null == customer_hash) {
            return "customer_data must be present!";
        }
        if (customer_hash instanceof Customer) {
            params.put("customer_data", ((Customer)customer_hash).serialize());
        }

        Object collateral_hash = params.get("collateral_data");
        if (null == collateral_hash) {
            return "collateral_data must be present!";
        }
        if (collateral_hash instanceof Collateral) {
            params.put("collateral_data", ((Collateral)collateral_hash).serialize());
        }

        Object loan_detail_hash = params.get("loan_data");
        if (null == loan_detail_hash) {
            return "loan_data must be present!";
        }
        if (loan_detail_hash instanceof Detail) {
            params.put("loan_data", ((Detail)loan_detail_hash).serialize());
        }

        Object installment_hash = params.get("installment_data");
        if (null == installment_hash) {
            return "installment_data must be present!";
        }
        if (installment_hash instanceof Installment) {
            params.put("installment_data", ((Installment)installment_hash).serialize());
        }

        Object company_management_hash = params.get("company_management_data");
        if (null != company_management_hash && company_management_hash instanceof CompanyManagement) {
            params.put("company_management_data", ((CompanyManagement)company_management_hash).serialize());
        }

        String url = loanURL;
        String response = APIResource.request(APIResource.RequestMethod.POST, url, params, userApiToken);
        return response;
    }

    private String repaymentURL(String repaymentID) {
        String url = loanURL + "/" + id + "/repayments";
        if (null != repaymentID) {
            url += "/" + repaymentID;
        }
        return url;
    }

    public String createRepayment(BigDecimal amount, BigDecimal collectionFee, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("amount", amount);
        params.put("collection_fee", collectionFee);
        String url = repaymentURL(null);
        String response = APIResource.request(APIResource.RequestMethod.POST, url, params, userApiToken);
        return response;
    }

    public Repayment getRepayment(String repaymentID, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = repaymentURL(repaymentID);
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, userApiToken);

        return new Gson().fromJson(response, Repayment.class);
    }

    /** This class is a temporary class to help formatting repayments response
     * which is { repayments: [] } */
    private class RepaymentList {
        public List<Repayment> repayments;
    }

    public List<Repayment> getRepayments(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = repaymentURL(null);
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, userApiToken);

        return new Gson().fromJson(response, RepaymentList.class).repayments;
    }

    public static void outstandingLoans(Integer page, Integer perPage, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("page", page);
        params.put("per_page", perPage);
        String url = loanURL + "/reconciliations";
        APIResource.request(APIResource.RequestMethod.POST, url, params, userApiToken);
    }

    public static void outstandingLoanRepayments(Date transactionDate, Integer page, Integer perPage, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("transaction_date", new SimpleDateFormat("yyyy-MM-dd").format(transactionDate));
        params.put("page", page);
        params.put("per_page", perPage);
        String url = loanURL + "/reconciliations";
        APIResource.request(APIResource.RequestMethod.POST, url, params, userApiToken);
    }

    public static List<Loan> parseOutstandingLoans(String response) {
        return Arrays.asList(new Gson().fromJson(response, Loan[].class));
    }

    public static List<Repayment> parseOutstandingRepayments(String response) {
        return new Gson().fromJson(response, RepaymentList.class).repayments;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getLastRacErrorReason() {
        return lastRacErrorReason;
    }

    public String getLastSlikErrorReason() {
        return lastSlikErrorReason;
    }

    public String getLastWithdrawalOnBehalfErrorReason() {
        return lastWithdrawalOnBehalfErrorReason;
    }

    public String getLastDisbursementReportErrorReason() {
        return lastDisbursementReportErrorReason;
    }

    public Collateral getCollateral() {
        return collateral;
    }

    public CompanyManagement getCompanyManagement() {
        return companyManagement;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Detail getDetail() {
        return detail;
    }

    public Installment getInstallment() {
        return installment;
    }
}
