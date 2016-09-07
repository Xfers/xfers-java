package com.xfers.exception;

public class AuthenticationException extends XfersException {

    public AuthenticationException(String message, String requestId, Integer statusCode) {
        super(message, requestId, statusCode);
    }
}
