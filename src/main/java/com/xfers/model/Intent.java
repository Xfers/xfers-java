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

public class Intent {
    private static final String resourceUrl = "/intents";

    @SerializedName("unique_amount") private String uniqueAmount;
    @SerializedName("checkout_url") private String checkoutUrl;
    @SerializedName("request_id") private String requestId;
    @SerializedName("notify_url") private String notifyUrl;
    @SerializedName("bank_name") private String bankName;
    @SerializedName("bank_abbrev") private String bankAbbrev;
    @SerializedName("account_name") private String accountName;
    @SerializedName("bank_account_no") private String bankAccountNo;
    @SerializedName("expiration_date") private String expirationDate;

    private String id;
    private BigDecimal amount;
    private String currency;
    private String bank;
    private String status;

    public static Intent retrieve(String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.GET, resourceUrl, null, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Intent.class);
    }

    public static Intent retrieve()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return retrieve(null);
    }

    public static Intent create(Map<String, Object> params, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.POST, resourceUrl, params, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Intent.class);
    }

    public static Intent create(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return create(params, null);
    }

    public static Intent cancel(String id, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + id + "/cancel";
        String response = APIResource.request(APIResource.RequestMethod.POST, url, null, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Intent.class);
    }

    public static Intent cancel(String id)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        if (Strings.isNullOrEmpty(id)) {
            throw new InvalidRequestException("Intent id cannot be empty", 400);
        }
        return cancel(id, null);
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAbbrev() {
        return bankAbbrev;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public String getExpirationDate() {
        return expirationDate;
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
