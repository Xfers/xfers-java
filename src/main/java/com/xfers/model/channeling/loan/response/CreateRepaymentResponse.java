package com.xfers.model.channeling.loan.response;

import java.math.BigDecimal;
import java.util.Date;

public class CreateRepaymentResponse {
    private String loanId;
    private String loanRepaymentId;
    private String status;
    private BigDecimal amount;
    private BigDecimal collectionFee;
    private Date createdAt;

    public String getLoanID() {
        return this.loanId;
    }

    public String getLoanRepaymentID() {
        return this.loanRepaymentId;
    }

    public String getStatus() {
        return this.status;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public BigDecimal getCollectionFee() {
        return this.collectionFee;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
}
