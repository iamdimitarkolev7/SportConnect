package com.connect.sport.authentication.exception;

public class PasswordDoNotMatchException extends RuntimeException {

    public PasswordDoNotMatchException(String message) {
        super(message);
    }
}
