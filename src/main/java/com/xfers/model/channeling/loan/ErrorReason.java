package com.xfers.model.channeling.loan;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ErrorReason {
    @SerializedName("CODE") private String code;
    @SerializedName("DESC") private String description;
    @SerializedName("DATA") private List<ErrorData> data;

    public class ErrorData {
        @SerializedName("TID") private String tid;
        @SerializedName("REFNO") private String refno;
        @SerializedName("LOCID") private String locid;
        @SerializedName("ACCID") private String accid;

        public String getTid() {
            return this.tid;
        }
    
        public String getRefno() {
            return this.refno;
        }
    
        public String getLocid() {
            return this.locid;
        }

        public String getAccid() {
            return this.accid;
        }
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public List<ErrorData> getData() {
        return this.data;
    }
}
