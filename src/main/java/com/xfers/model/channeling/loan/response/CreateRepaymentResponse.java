package com.xfers.model.channeling.loan.response;

public class CreateRepaymentResponse {
    private String loanId;
    private String loanRepaymentId;
    private Boolean success;
    private String notes;

    public String getLoanID() {
        return this.loanId;
    }

    public String getLoanRepaymentID() {
        return this.loanRepaymentId;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public Boolean isSuccess() {
        return this.success;
    }

    public String getNotes() {
        return this.notes;
    }
}
