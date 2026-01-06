package com.radud.batwatch.exception;

public class UserNotFoundException extends ModelEntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
