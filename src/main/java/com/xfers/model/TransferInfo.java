package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class TransferInfo {
    protected static final String resourceUrl = "/user/transfer_info";

    @SerializedName("bank_name_full") private String bankNameFull;
    @SerializedName("bank_name_abbreviation") private String bankNameAbbreviation;
    @SerializedName("bank_account_no") private String bankAccountNo;
    @SerializedName("bank_code") private String bankCode;
    @SerializedName("branch_code") private String branchCode;
    @SerializedName("branch_area") private String branchArea;
    @SerializedName("unique_id") private String uniqueId;

    public String getBankNameFull() {
        return bankNameFull;
    }

    public String getBankNameAbbreviation() {
        return bankNameAbbreviation;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getBranchArea() {
        return branchArea;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
