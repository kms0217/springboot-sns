package com.kms.mygram.exception;

public class ApiException extends RuntimeException {

    public ApiException(String msg) {
        super(msg);
    }
}
