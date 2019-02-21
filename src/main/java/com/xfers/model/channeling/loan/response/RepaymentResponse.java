package com.xfers.model.channeling.loan.response;

import java.math.BigDecimal;
import java.util.Date;

import com.xfers.model.channeling.loan.Repayment;

public class RepaymentResponse {
    private String id;
    private String loanId;
    private String status;
    private BigDecimal amount;
    private BigDecimal collectionFee;
    private Date createdAt;
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

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Repayment getPartnerBankResponse() {
        if (null != this.partnerBankResponse && null == this.partnerBankResponse.getId()) {
            this.partnerBankResponse.setId(id);
        }
        return this.partnerBankResponse;
    }
}
