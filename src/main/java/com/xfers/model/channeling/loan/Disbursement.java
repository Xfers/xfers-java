package com.xfers.model.channeling.loan;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

public class Disbursement {
    private String id;
    private String idempotencyId;
    private String type;
    private String status;
    private BigDecimal amount;
    private BigDecimal fees;
    private String accountNo;
    private String bankAbbrev;
    private Boolean express;
    private String valueDate;
    private String failureReason;
    private String walletName;
    private String arrival;
    private String comment;
    private String createdAt;

    public String mockStatusChange(String status, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idempotency_id", idempotencyId);
        params.put("status", status);

        String url = "/user/bank_account/withdrawal_requests/mock_result";
        String response = APIResource.request(APIResource.RequestMethod.PUT, url, params, userApiToken);
        return response;
    }

    public String getId() {
        return this.id;
    }

    public String getIdempotencyId() {
        return this.idempotencyId;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public BigDecimal getFees() {
        return this.fees;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public String getBankAbbrev() {
        return this.bankAbbrev;
    }

    public Boolean getExpress() {
        return this.express;
    }

    public Boolean isExpress() {
        return this.express;
    }

    public String getValueDate() {
        return this.valueDate;
    }

    public String getFailureReason() {
        return this.failureReason;
    }

    public String getWalletName() {
        return this.walletName;
    }

    public String getArrival() {
        return this.arrival;
    }

    public String getComment() {
        return this.comment;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }
}
