package com.xfers.model.channeling.loan.response;

import java.math.BigDecimal;

import com.xfers.model.channeling.loan.Disbursement;

public class CreateDisbursementResponse {
    private String loanId;
    private String loanStatus;
    private BigDecimal availableBalance;
    private BigDecimal ledgerBalance;
    private Disbursement disbursement;

    public String getLoanId() {
        return this.loanId;
    }

    public String getLoanStatus() {
        return this.loanStatus;
    }

    public BigDecimal getAvailableBalance() {
        return this.availableBalance;
    }

    public BigDecimal getLedgerBalance() {
        return this.ledgerBalance;
    }

    public Disbursement getDisbursement() {
      return this.disbursement;
    }
}
