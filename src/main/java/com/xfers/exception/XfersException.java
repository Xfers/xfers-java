package com.xfers.exception;

public abstract class XfersException extends Exception {

    private Integer statusCode;

    public XfersException(String message, Integer statusCode) {
        super(message, null);
        this.statusCode = statusCode;
    }

    public XfersException(String message) {
        super(message, null);
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}