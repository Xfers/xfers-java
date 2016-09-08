package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Activity {
    protected static final String resourceUrl = "/user/activities";

    @SerializedName("trans_type") private String transType;
    @SerializedName("display_time") private String displayTime;
    @SerializedName("plus_minus") private String plusMinus;
    @SerializedName("display_amount") private String displayAmount;
    @SerializedName("transaction_items") private List<TransactionItem> transactionItems;

    private String type;
    private String description;

    public String getTransType() {
        return transType;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public String getPlusMinus() {
        return plusMinus;
    }

    public String getDisplayAmount() {
        return displayAmount;
    }

    public List<TransactionItem> getTransactionItems() {
        return transactionItems;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
