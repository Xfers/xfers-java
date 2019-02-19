package com.xfers.model.response;

public class ConnectResponse {
    private String msg;
    private String id;
    private String userApiToken;
    private String currency;
    private String signUpUrl;
    private String walletName;
    private Boolean isFullyVerified;
    private String otpProvided;

    public String getMessage() {
        return this.msg;
    }

    public String getId() {
        return this.id;
    }

    public String getUserApiToken() {
        return this.userApiToken;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getSignUpUrl() {
        return this.signUpUrl;
    }

    public String getWalletName() {
        return this.walletName;
    }

    public Boolean isFullyVerified() {
        return this.isFullyVerified;
    }

    public String getOtpProvided() {
        return this.otpProvided;
    }
}
