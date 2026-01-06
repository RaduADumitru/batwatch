package com.radud.batwatch.exception;

public class AuthenticatedUserNotFoundException extends RuntimeException {
    public AuthenticatedUserNotFoundException(String message) {
        super(message);
    }
}
