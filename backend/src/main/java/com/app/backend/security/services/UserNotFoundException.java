package com.app.backend.security.services;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String s) {
        super(s);
    }
}
