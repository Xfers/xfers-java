package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

import java.math.BigDecimal;
import java.util.*;

public class Charge {
    private static final String resourceUrl = "/charges";

    @SerializedName("checkout_url") private String checkoutUrl;
    @SerializedName("notify_url") private String notifyUrl;
    @SerializedName("return_url") private String returnUrl;
    @SerializedName("cancel_url") private String cancelUrl;
    @SerializedName("order_id") private String orderId;
    @SerializedName("statement_descriptor") private String statementDescriptor;
    @SerializedName("receipt_email") private String receiptEmail;
    @SerializedName("total_amount") private String totalAmount;
    @SerializedName("meta_data") private Object metaData;

    private String id;
    private BigDecimal amount;
    private String currency;
    private String customer;
    private Boolean capture;
    private Boolean refundable;
    private String description;
    private List<Item> items;
    private BigDecimal shipping;
    private BigDecimal tax;
    private BigDecimal fees;
    private String status;

    public static List<Charge> listAll(Map<String, Object> params, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.GET, resourceUrl, params, connectKey);
        Gson gson = new Gson();
        return new ArrayList<Charge>(Arrays.asList(gson.fromJson(response, Charge[].class)));
    }

    public static List<Charge> listAll(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return listAll(params, null);
    }

    public static List<Charge> listAll()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return listAll(null, null);
    }

    public static Charge retrieve(String id, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + id;
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Charge.class);
    }

    public static Charge retrieve(String id)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return retrieve(id, null);
    }

    public static Charge create(Map<String, Object> params, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.POST, resourceUrl, params, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Charge.class);
    }

    public static Charge create(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return create(params, null);
    }

    public static Charge cancel(String id, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + id + "/cancel";
        String response = APIResource.request(APIResource.RequestMethod.POST, url, null, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Charge.class);
    }

    public static Charge cancel(String id)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return cancel(id, null);
    }

    public static Charge refund(String id, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + id + "/refunds";
        String response = APIResource.request(APIResource.RequestMethod.POST, url, null, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Charge.class);
    }

    public static Charge refund(String id)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return refund(id, null);
    }

    public static String validate(String transactionId, Map<String, Object> params, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + transactionId + "/validate";
        String str = APIResource.request(APIResource.RequestMethod.POST, url, params, connectKey);
        Gson gson = new Gson();
        Response response = gson.fromJson(str, Response.class);
        return response.getMsg();
    }

    public static String validate(String transactionId, Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return validate(transactionId, params, null);
    }

    public static Charge authorize(String chargeId, String authCode)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("auth_code", authCode);

        String url = resourceUrl + "/" + chargeId + "/authorize";
        String str = APIResource.request(APIResource.RequestMethod.POST, url, params, null);
        Gson gson = new Gson();
        return gson.fromJson(str, Charge.class);
    }


    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public String getReceiptEmail() {
        return receiptEmail;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public Object getMetaData() {
        return metaData;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCustomer() {
        return customer;
    }

    public Boolean getCapture() {
        return capture;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public String getDescription() {
        return description;
    }

    public List<Item> getItems() {
        return items;
    }

    public BigDecimal getShipping() {
        return shipping;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
