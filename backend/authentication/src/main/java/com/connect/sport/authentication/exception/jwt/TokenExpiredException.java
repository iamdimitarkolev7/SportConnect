package com.connect.sport.authentication.exception.jwt;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String msg) {
        super(msg);
    }
}
