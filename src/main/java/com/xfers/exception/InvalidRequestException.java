package com.xfers.exception;

public class InvalidRequestException extends XfersException {

    public InvalidRequestException(String message, Integer statusCode) {
        super(message, statusCode);
    }

}