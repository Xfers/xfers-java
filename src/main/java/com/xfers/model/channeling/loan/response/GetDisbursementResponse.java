package com.xfers.model.channeling.loan.response;

import com.xfers.model.channeling.loan.Disbursement;

public class GetDisbursementResponse {
    private String loanID;
    private String loanStatus;
    private Disbursement disbursement;

    public String getLoanID() {
        return this.loanID;
    }

    public String getLoanStatus() {
        return this.loanStatus;
    }

    public Disbursement getDisbursement() {
        return this.disbursement;
    }
}
