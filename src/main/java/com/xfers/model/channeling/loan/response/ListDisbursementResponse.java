package com.xfers.model.channeling.loan.response;

import java.util.List;

import com.xfers.model.channeling.loan.Disbursement;

public class ListDisbursementResponse {
    private String loanId;
    private String loanStatus;
    private List<Disbursement> disbursements;

    public String getLoanId() {
        return this.loanId;
    }

    public String getLoanStatus() {
        return this.loanStatus;
    }

    public List<Disbursement> getDisbursements() {
        return this.disbursements;
    }
}
