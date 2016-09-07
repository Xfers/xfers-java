package com.xfers.exception;

public abstract class XfersException extends Exception {

    private String requestId;
    private Integer statusCode;

    public XfersException(String message, String requestId, Integer statusCode) {
        super(message, null);
        this.requestId = requestId;
        this.statusCode = statusCode;
    }

    public XfersException(String message, String requestId, Integer statusCode, Throwable e) {
        super(message, e);
        this.statusCode = statusCode;
        this.requestId = requestId;
    }

    private static final long serialVersionUID = 1L;

    public String getRequestId() {
        return requestId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String toString() {
        String reqIdStr = "";
        if (requestId != null) {
            reqIdStr = "; request-id: " + requestId;
        }
        return super.toString() + reqIdStr;
    }
}