package com.example.my.common.exception;

@Deprecated
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

}
