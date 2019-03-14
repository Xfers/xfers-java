package com.xfers.model.channeling.loan.response;

import java.math.BigDecimal;
import java.util.List;

public class LoanReconciliationResponse {
    private String CODE;
    private String DESC;
    private String CURRENTPAGE;
    private Integer TOTALPAGES;
    private Integer TOTALROWS;
    private BigDecimal OSPRINCIPALTOTAL;
    private BigDecimal OSINTERESTTOTAL;
    private BigDecimal PRINCIPALTOTAL;
    private BigDecimal INTERESTTOTAL;
    private List<ReconData> DATA;

    public class LoanReconciliation {
        private Integer ROWNUMBER;
        private String REFNO;
        private BigDecimal OSPRINCIPALREAL;
        private BigDecimal OSINTERESTREAL;
        private String DPD;
        private String ACCNO;
        private String BOOKINGDATE;
        private BigDecimal PRINCIPAL;
        private BigDecimal INTEREST;

        public Integer getRownumber() {
            return this.ROWNUMBER;
        }

        public String getRefno() {
            return this.REFNO;
        }

        public BigDecimal getOsprincipalreal() {
            return this.OSPRINCIPALREAL;
        }

        public BigDecimal getOsinterestreal() {
            return this.OSINTERESTREAL;
        }

        public String getDpd() {
            return this.DPD;
        }

        public String getAccno() {
            return this.ACCNO;
        }

        public String getBookingdate() {
            return this.BOOKINGDATE;
        }

        public BigDecimal getPrincipal() {
            return this.PRINCIPAL;
        }

        public BigDecimal getInterest() {
            return this.INTEREST;
        }
    }

    private class ReconData {
        public List<LoanReconciliation> RECONDATA;
    }

    public String getCode() {
        return this.CODE;
    }

    public String getDesc() {
        return this.DESC;
    }

    public String getCurrentpage() {
        return this.CURRENTPAGE;
    }

    public Integer getTotalpages() {
        return this.TOTALPAGES;
    }

    public Integer getTotalrows() {
        return this.TOTALROWS;
    }

    public BigDecimal getOsprincipaltotal() {
        return this.OSPRINCIPALTOTAL;
    }

    public BigDecimal getOsinteresttotal() {
        return this.OSINTERESTTOTAL;
    }

    public BigDecimal getPrincipaltotal() {
        return this.PRINCIPALTOTAL;
    }

    public BigDecimal getInteresttotal() {
        return this.INTERESTTOTAL;
    }

    public List<LoanReconciliation> getLoanReconciliations() {
        return this.DATA.get(0).RECONDATA;
    }
}
