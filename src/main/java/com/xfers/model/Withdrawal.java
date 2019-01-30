package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Withdrawal {
    @SerializedName("account_no") private String accountNo;
    @SerializedName("bank_abbrev") private String bankAbbrev;
    @SerializedName("failure_reason") private String failureReason;

    private String id;
    private BigDecimal amount;
    private BigDecimal fees;
    private Boolean express;
    private String status;
    private String arrival;
    private String comment;

    public String getAccountNo() {
        return accountNo;
    }

    public String getBankAbbrev() {
        return bankAbbrev;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public Boolean getExpress() {
        return express;
    }

    public String getStatus() {
        return status;
    }

    public String getArrival() {
        return arrival;
    }

    public String getFailureReason() {
        return failureReason;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
