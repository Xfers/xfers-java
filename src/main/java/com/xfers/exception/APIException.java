package com.xfers.exception;

public class APIException extends XfersException {

    public APIException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}