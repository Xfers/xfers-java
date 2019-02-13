package com.xfers.model.channeling.loan;

import java.math.BigDecimal;
import java.util.Date;

public class Disbursement {
    private String id;
    private String idempotencyID;
    private String type;
    private String status;
    private BigDecimal amount;
    private BigDecimal fees;
    private String accountNo;
    private String bankAbbrev;
    private Boolean express;
    private Date valueDate;
    private String failureReason;
    private String walletName;
    private String arrival;
    private String comment;
    private Date createdAt;

    public String getId() {
        return this.id;
    }

    public String getIdempotencyID() {
        return this.idempotencyID;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public BigDecimal getFees() {
        return this.fees;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public String getBankAbbrev() {
        return this.bankAbbrev;
    }

    public Boolean getExpress() {
        return this.express;
    }

    public Boolean isExpress() {
        return this.express;
    }

    public Date getValueDate() {
        return this.valueDate;
    }

    public String getFailureReason() {
        return this.failureReason;
    }

    public String getWalletName() {
        return this.walletName;
    }

    public String getArrival() {
        return this.arrival;
    }

    public String getComment() {
        return this.comment;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
}
