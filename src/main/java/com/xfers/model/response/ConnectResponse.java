package com.xfers.model.response;

public class ConnectResponse {
    private String msg;
    private String id;
    private String userApiToken;
    private String signUpUrl;
    private Boolean isFullyVerified;

    public String getMessage() {
        return this.msg;
    }

    public String getId() {
        return this.id;
    }

    public String getUserApiToken() {
        return this.userApiToken;
    }

    public String getSignUpUrl() {
        return this.signUpUrl;
    }

    public Boolean isFullyVerified() {
        return this.isFullyVerified;
    }
}
