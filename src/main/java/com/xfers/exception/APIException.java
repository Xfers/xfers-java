package com.xfers.exception;

public class APIException extends XfersException {

    public APIException(String message, String requestId, Integer statusCode, Throwable e) {
        super(message, requestId, statusCode, e);
    }

}