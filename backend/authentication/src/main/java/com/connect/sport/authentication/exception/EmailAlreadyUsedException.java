package com.connect.sport.authentication.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
