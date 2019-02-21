package com.xfers.model.channeling.loan;

import java.math.BigDecimal;
import java.util.Date;

public class Repayment {
    private String id;
    private Boolean success;
    private String refno;
    private String accno;
    private String accountname;
    private String period;
    private Date duedate;
    private BigDecimal installment;
    private BigDecimal installfeeaccount;
    private String dpd;
    private BigDecimal osprincipal;
    private BigDecimal osinterest;
    private BigDecimal ostotal;
    private BigDecimal overdueprincipal;
    private BigDecimal overdueinterest;
    private BigDecimal overduetotal;
    private BigDecimal penalty;
    private BigDecimal overdueinstallfee;
    private BigDecimal totaloverdueandpenalty;
    private String failurereason;

    /**
     * This function shouldn't be called. It is used only in deserializing CreateRepaymentResponse.
     */
    public Repayment setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getRefno() {
        return refno;
    }

    public String getAccno() {
        return accno;
    }

    public String getAccountname() {
        return accountname;
    }

    public String getPeriod() {
        return period;
    }

    public Date getDuedate() {
        return duedate;
    }

    public BigDecimal getInstallment() {
        return installment;
    }

    public BigDecimal getInstallfeeaccount() {
        return installfeeaccount;
    }

    public String getDpd() {
        return dpd;
    }

    public BigDecimal getOsprincipal() {
        return osprincipal;
    }

    public BigDecimal getOsinterest() {
        return osinterest;
    }

    public BigDecimal getOstotal() {
        return ostotal;
    }

    public BigDecimal getOverdueprincipal() {
        return overdueprincipal;
    }

    public BigDecimal getOverdueinterest() {
        return overdueinterest;
    }

    public BigDecimal getOverduetotal() {
        return overduetotal;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public BigDecimal getOverdueinstallfee() {
        return overdueinstallfee;
    }

    public BigDecimal getTotaloverdueandpenalty() {
        return totaloverdueandpenalty;
    }

    public String getFailurereason() {
        return failurereason;
    }
}
