package com.xfers.model.channeling.loan;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.model.channeling.loan.response.CreateDisbursementResponse;
import com.xfers.model.channeling.loan.response.GetDisbursementResponse;
import com.xfers.model.channeling.loan.response.ListDisbursementResponse;
import com.xfers.model.channeling.loan.response.ListRepaymentResponse;
import com.xfers.model.channeling.loan.response.RepaymentResponse;
import com.xfers.net.APIResource;
import com.xfers.serializer.SnakeToCamelDeserializer;
import com.xfers.serializer.YearMonthDateSerializer;

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

    public Loan() { }

    /**
     * Instantiate an empty loan object with only ID.
     * This constructor do nothing other than set the initial loan ID.
     * Useful for creating or getting repayments or disbursements.
     */
    public Loan(String id) {
        this.id = id;
    }

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

        String stringParams = YearMonthDateSerializer.create().toJson(params);

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
            params.put("customer_data", YearMonthDateSerializer.create().toJson((Customer)customer_hash));
        }

        Object collateral_hash = params.get("collateral_data");
        if (null == collateral_hash) {
            return "collateral_data must be present!";
        }
        if (collateral_hash instanceof Collateral) {
            params.put("collateral_data", YearMonthDateSerializer.create().toJson((Collateral)collateral_hash));
        }

        Object loan_detail_hash = params.get("loan_data");
        if (null == loan_detail_hash) {
            return "loan_data must be present!";
        }
        if (loan_detail_hash instanceof Detail) {
            params.put("loan_data", YearMonthDateSerializer.create().toJson((Detail)loan_detail_hash));
        }

        Object installment_hash = params.get("installment_data");
        if (null == installment_hash) {
            return "installment_data must be present!";
        }
        if (installment_hash instanceof Installment) {
            params.put("installment_data", YearMonthDateSerializer.create().toJson((Installment)installment_hash));
        }

        Object company_management_hash = params.get("company_management_data");
        if (null != company_management_hash && company_management_hash instanceof CompanyManagement) {
            params.put("company_management_data", YearMonthDateSerializer.create().toJson((CompanyManagement)company_management_hash));
        }

        String url = loanURL;
        String response = APIResource.request(APIResource.RequestMethod.POST, url, params, userApiToken);
        return response;
    }

    public CreateDisbursementResponse createDisbursement(Map<String, Object> params, String xfersAppApiKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = loanURL + "/" + id + "/disbursements";
        String response = APIResource.request(APIResource.RequestMethod.POST, url, params, xfersAppApiKey);
        return SnakeToCamelDeserializer.create().fromJson(response, CreateDisbursementResponse.class);
    }

    public GetDisbursementResponse getDisbursement(String contractId, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = loanURL + "/" + id + "/disbursements/" + contractId;
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, userApiToken);
        return SnakeToCamelDeserializer.create().fromJson(response, GetDisbursementResponse.class);
    }

    public ListDisbursementResponse getAllDisbursements(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = loanURL + "/" + id + "/disbursements";
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, userApiToken);
        return SnakeToCamelDeserializer.create().fromJson(response, ListDisbursementResponse.class);
    }

    public String createDisbursementReport(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = loanURL + "/" + id + "/disbursement_reports";
        String response = APIResource.request(APIResource.RequestMethod.POST, url, null, userApiToken);
        return response;
    }

    private String repaymentURL(String repaymentId) {
        String url = loanURL + "/" + id + "/repayments";
        if (null != repaymentId) {
            url += "/" + repaymentId;
        }
        return url;
    }

    public RepaymentResponse createRepayment(BigDecimal amount, BigDecimal collectionFee, String idempotencyId, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("amount", amount);
        params.put("collection_fee", collectionFee);
        params.put("idempotency_id", idempotencyId);
        String url = repaymentURL(null);
        String response = APIResource.request(APIResource.RequestMethod.POST, url, params, userApiToken);
        return SnakeToCamelDeserializer.create().fromJson(response, RepaymentResponse.class);
    }

    public RepaymentResponse getRepayment(String repaymentId, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = repaymentURL(repaymentId);
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, userApiToken);

        return SnakeToCamelDeserializer.create().fromJson(response, RepaymentResponse.class);
    }

    public List<RepaymentResponse> getAllRepayments(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = repaymentURL(null);
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, userApiToken);

        return SnakeToCamelDeserializer.create().fromJson(response, ListRepaymentResponse.class).getRepayments();
    }

    public static void outstandingLoans(Integer page, Integer perPage, String xfersAppApiKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("page", page);
        params.put("per_page", perPage);
        String url = loanURL + "/reconciliations";
        APIResource.request(APIResource.RequestMethod.POST, url, params, xfersAppApiKey);
    }

    public static void outstandingLoanRepayments(Date transactionDate, Integer page, Integer perPage, String xfersAppApiKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("transaction_date", new SimpleDateFormat("yyyy-MM-dd").format(transactionDate));
        params.put("page", page);
        params.put("per_page", perPage);
        String url = loanURL + "/repayments/reconciliations";
        APIResource.request(APIResource.RequestMethod.POST, url, params, xfersAppApiKey);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Loan setId(String id) {
        this.id = id;
        return this;
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
