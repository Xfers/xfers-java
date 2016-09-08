package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class TransactionItem {
    @SerializedName("item_name") private String itemName;
    @SerializedName("item_description") private String itemDescription;
    @SerializedName("item_price") private BigDecimal itemPrice;
    @SerializedName("item_quantity") private Integer itemQuantity;

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
