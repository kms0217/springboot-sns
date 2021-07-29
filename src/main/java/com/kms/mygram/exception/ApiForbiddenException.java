package com.kms.mygram.exception;

public class ApiForbiddenException extends RuntimeException {

    public ApiForbiddenException(String message) {
        super(message);
    }
}
