package com.kms.mygram.common.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuthException extends AuthenticationException {

    public OAuthException(String message) { super(message); }
}
