package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("available_balance") private String availableBalance;
    @SerializedName("ledger_balance") private String ledgerBalance;
    @SerializedName("withdrawal_request") private Withdrawal withdrawalRequest;
    @SerializedName("withdrawal_requests") private List<Withdrawal> withdrawalRequests;
    @SerializedName("user_api_token") private String userApiToken;
    @SerializedName("sign_up_url") private String signUpUrl;
    @SerializedName("return_url") private String returnUrl;

    @SerializedName("callback_url") private String callbackURL;
    @SerializedName("callback_id") private int callbackId;
    @SerializedName("name") private String name;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("events") private List<String> events;

    private Boolean success;
    private String msg;
    private String error;
    private List<Activity> activities;

    public String getAvailableBalance() {
        return availableBalance;
    }

    public String getLedgerBalance() {
        return ledgerBalance;
    }

    public Withdrawal getWithdrawalRequest() {
        return withdrawalRequest;
    }

    public List<Withdrawal> getWithdrawalRequests() {
        return withdrawalRequests;
    }

    public String getMsg() {
        return msg;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public String getUserApiToken() {
        return userApiToken;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public String getReturnUrl() {
        return returnUrl;
    }
}
