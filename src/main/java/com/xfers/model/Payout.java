package com.xfers.model;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Payout {
    private static final String resourceUrl = "/payouts";

    @SerializedName("invoice_id") private String invoiceId;
    @SerializedName("bank_account_no") private String bankAccountNo;
    @SerializedName("created_date") private String createdDate;
    @SerializedName("completed_date") private String completedDate;

    private String id;
    private String recipient;
    private BigDecimal amount;
    private String currency;
    private String descriptions;
    private String bank;
    private String status;


    public static List<Payout> listAll(Map<String, Object> params, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.GET, resourceUrl, params, userApiToken);
        Gson gson = new Gson();
        return new ArrayList<Payout>(Arrays.asList(gson.fromJson(response, Payout[].class)));
    }

    public static List<Payout> listAll(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return listAll(params, null);
    }

    public static List<Payout> listAll()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return listAll(null, null);
    }

    public static Payout retrieve(String id, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + id;
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, userApiToken);
        Gson gson = new Gson();
        return gson.fromJson(response, Payout.class);
    }

    public static Payout retrieve(String id)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        if (Strings.isNullOrEmpty(id)) {
            throw new InvalidRequestException("Payout id cannot be empty", 400);
        }
        return retrieve(id, null);
    }

    public static Payout create(Map<String, Object> params, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.POST, resourceUrl, params, userApiToken);
        Gson gson = new Gson();
        return gson.fromJson(response, Payout.class);
    }

    public static Payout create(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return create(params, null);
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public String getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String getBank() {
        return bank;
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
