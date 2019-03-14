package com.xfers.model.channeling.loan.response;

import java.math.BigDecimal;
import java.util.List;

public class RepaymentReconciliationResponse {
    private String CODE;
    private String DESC;
    private String CURRENTPAGE;
    private Integer TOTALPAGES;
    private Integer TOTALROWS;
    private BigDecimal TOTALAMOUNT;
    private BigDecimal TOTALPRINCIPAL;
    private BigDecimal TOTALINTEREST;
    private BigDecimal TOTALINSTALLFEE;
    private BigDecimal TOTALCOLLECTIONFEE;
    private List<ReconData> DATA;

    public class RepaymentReconciliation {
        private Integer ROWNUMBER;
        private String REFNO;
        private String TID;
        private String TXNDATE;
        private BigDecimal AMOUNT;
        private BigDecimal COLLECTIONFEE;
        private BigDecimal PRINCIPAL;
        private BigDecimal INTEREST;
        private BigDecimal INSTALLFEE;
        private String PAYMENTID;
        private String PAYMENTSTATUS;
        private String STATUSDESC;

        public Integer getRownumber() {
            return this.ROWNUMBER;
        }

        public String getRefno() {
            return this.REFNO;
        }

        public String getTid() {
            return this.TID;
        }

        public String getTxndate() {
            return this.TXNDATE;
        }

        public BigDecimal getAmount() {
            return this.AMOUNT;
        }

        public BigDecimal getCollectionfee() {
            return this.COLLECTIONFEE;
        }

        public BigDecimal getPrincipal() {
            return this.PRINCIPAL;
        }

        public BigDecimal getInterest() {
            return this.INTEREST;
        }

        public BigDecimal getInstallfee() {
            return this.INSTALLFEE;
        }

        public String getPaymentid() {
            return this.PAYMENTID;
        }

        public String getPaymentstatus() {
            return this.PAYMENTSTATUS;
        }

        public String getStatusdesc() {
            return this.STATUSDESC;
        }
    }

    private class ReconData {
        public List<RepaymentReconciliation> RECONDATA;
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

    public BigDecimal getTotalamount() {
        return this.TOTALAMOUNT;
    }

    public BigDecimal getTotalprincipal() {
        return this.TOTALPRINCIPAL;
    }

    public BigDecimal getTotalinterest() {
        return this.TOTALINTEREST;
    }

    public BigDecimal getTotalinstallfee() {
        return this.TOTALINSTALLFEE;
    }

    public BigDecimal getTotalcollectionfee() {
        return this.TOTALCOLLECTIONFEE;
    }

    public List<RepaymentReconciliation> getRepaymentReconciliations() {
        return this.DATA.get(0).RECONDATA;
    }
}
