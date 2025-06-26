package com.license.backend.exception;

public class FailedLoginException extends RuntimeException{

    public FailedLoginException(String message) {
        super(message);
    }

}
