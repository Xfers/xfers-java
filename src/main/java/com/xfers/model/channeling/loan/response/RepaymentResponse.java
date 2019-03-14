package com.xfers.model.channeling.loan.response;

import java.math.BigDecimal;

import com.xfers.model.channeling.loan.Repayment;

public class RepaymentResponse {
    private String id;
    private String loanId;
    private String status;
    private BigDecimal amount;
    private BigDecimal collectionFee;
    private String createdAt;
    private Repayment partnerBankResponse;

    public String getLoanRepaymentId() {
        return this.id;
    }

    public String getLoanId() {
        return this.loanId;
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

    public String getCreatedAt() {
        return this.createdAt;
    }

    public Repayment getPartnerBankResponse() {
        return this.partnerBankResponse;
    }
}
