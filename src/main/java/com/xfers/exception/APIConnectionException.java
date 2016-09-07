package com.xfers.exception;

public class APIConnectionException extends XfersException {

    public APIConnectionException(String message) {
        super(message, null, 0);
    }

    public APIConnectionException(String message, Throwable e) {
        super(message, null, 0, e);
    }
}