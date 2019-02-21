package com.xfers.model.channeling.loan.response;

import com.xfers.model.channeling.loan.Disbursement;

public class GetDisbursementResponse {
    private String loanId;
    private String loanStatus;
    private Disbursement disbursement;

    public String getLoanId() {
        return this.loanId;
    }

    public String getLoanStatus() {
        return this.loanStatus;
    }

    public Disbursement getDisbursement() {
        return this.disbursement;
    }
}
