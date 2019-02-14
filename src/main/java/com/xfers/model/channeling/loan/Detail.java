package com.xfers.model.channeling.loan;

import java.math.BigDecimal;
import java.util.Date;

public class Detail {
    private String refno;
    private BigDecimal objectvalue;
    private BigDecimal principaltotal;
    private Integer tenor;
    private Integer loantype;
    private BigDecimal effectiverate;
    private BigDecimal installment;
    private Date firstinstdate;
    private BigDecimal admfee;
    private String inscode;
    private String inspremi;
    private String insonloan;
    private Integer installmenttype;
    private String branchcode;
    private String typeofuseid;
    private String orientationofuseid;
    private String debiturcatid;
    private String portfoliocatid;
    private String credittypeid;
    private String creditattributeid;
    private String creditcategoryid;
    private String fincat;
    private BigDecimal installfeeaccount;

    public Detail refno(String refno) {
        this.refno = refno;
        return this;
    }

    public Detail objectvalue(BigDecimal objectvalue) {
        this.objectvalue = objectvalue;
        return this;
    }

    public Detail principaltotal(BigDecimal principaltotal) {
        this.principaltotal = principaltotal;
        return this;
    }

    public Detail tenor(Integer tenor) {
        this.tenor = tenor;
        return this;
    }

    public Detail loantype(Integer loantype) {
        this.loantype = loantype;
        return this;
    }

    public Detail effectiverate(BigDecimal effectiverate) {
        this.effectiverate = effectiverate;
        return this;
    }

    public Detail installment(BigDecimal installment) {
        this.installment = installment;
        return this;
    }

    public Detail firstinstdate(Date firstinstdate) {
        this.firstinstdate = firstinstdate;
        return this;
    }

    public Detail admfee(BigDecimal admfee) {
        this.admfee = admfee;
        return this;
    }

    public Detail inscode(String inscode) {
        this.inscode = inscode;
        return this;
    }

    public Detail inspremi(String inspremi) {
        this.inspremi = inspremi;
        return this;
    }

    public Detail insonloan(String insonloan) {
        this.insonloan = insonloan;
        return this;
    }

    public Detail installmenttype(Integer installmenttype) {
        this.installmenttype = installmenttype;
        return this;
    }

    public Detail branchcode(String branchcode) {
        this.branchcode = branchcode;
        return this;
    }

    public Detail typeofuseid(String typeofuseid) {
        this.typeofuseid = typeofuseid;
        return this;
    }

    public Detail orientationofuseid(String orientationofuseid) {
        this.orientationofuseid = orientationofuseid;
        return this;
    }

    public Detail debiturcatid(String debiturcatid) {
        this.debiturcatid = debiturcatid;
        return this;
    }

    public Detail portfoliocatid(String portfoliocatid) {
        this.portfoliocatid = portfoliocatid;
        return this;
    }

    public Detail credittypeid(String credittypeid) {
        this.credittypeid = credittypeid;
        return this;
    }

    public Detail creditattributeid(String creditattributeid) {
        this.creditattributeid = creditattributeid;
        return this;
    }

    public Detail creditcategoryid(String creditcategoryid) {
        this.creditcategoryid = creditcategoryid;
        return this;
    }

    public Detail fincat(String fincat) {
        this.fincat = fincat;
        return this;
    }

    public Detail installfeeaccount(BigDecimal installfeeaccount) {
        this.installfeeaccount = installfeeaccount;
        return this;
    }

    public String getRefno() {
        return refno;
    }

    public BigDecimal getObjectvalue() {
        return objectvalue;
    }

    public BigDecimal getPrincipaltotal() {
        return principaltotal;
    }

    public Integer getTenor() {
        return tenor;
    }

    public Integer getLoantype() {
        return loantype;
    }

    public BigDecimal getEffectiverate() {
        return effectiverate;
    }

    public BigDecimal getInstallment() {
        return installment;
    }

    public Date getFirstinstdate() {
        return firstinstdate;
    }

    public BigDecimal getAdmfee() {
        return admfee;
    }

    public String getInscode() {
        return inscode;
    }

    public String getInspremi() {
        return inspremi;
    }

    public String getInsonloan() {
        return insonloan;
    }

    public Integer getInstallmenttype() {
        return installmenttype;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public String getTypeofuseid() {
        return typeofuseid;
    }

    public String getOrientationofuseid() {
        return orientationofuseid;
    }

    public String getDebiturcatid() {
        return debiturcatid;
    }

    public String getPortfoliocatid() {
        return portfoliocatid;
    }

    public String getCredittypeid() {
        return credittypeid;
    }

    public String getCreditattributeid() {
        return creditattributeid;
    }

    public String getCreditcategoryid() {
        return creditcategoryid;
    }

    public String getFincat() {
        return fincat;
    }

    public BigDecimal getInstallfeeaccount() {
        return installfeeaccount;
    }
}
