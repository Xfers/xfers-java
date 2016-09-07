package com.xfers.exception;

public class InvalidRequestException extends XfersException {

    public InvalidRequestException(String message, String requestId, Integer statusCode, Throwable e) {
        super(message, requestId, statusCode, e);
    }

}