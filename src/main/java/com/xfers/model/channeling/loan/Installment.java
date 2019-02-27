package com.xfers.model.channeling.loan;

import java.math.BigDecimal;

public class Installment {
    private Integer period;
    private String duedate;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal installfee;

    public Installment period(Integer period) {
        this.period = period;
        return this;
    }

    public Installment duedate(String duedate) {
        this.duedate = duedate;
        return this;
    }

    public Installment principal(BigDecimal principal) {
        this.principal = principal;
        return this;
    }

    public Installment interest(BigDecimal interest) {
        this.interest = interest;
        return this;
    }

    public Installment installfee(BigDecimal installfee) {
        this.installfee = installfee;
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public String getDuedate() {
        return duedate;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getInstallfee() {
        return installfee;
    }
}
