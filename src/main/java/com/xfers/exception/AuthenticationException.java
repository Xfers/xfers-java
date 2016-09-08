package com.xfers.exception;

public class AuthenticationException extends XfersException {
    public AuthenticationException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
