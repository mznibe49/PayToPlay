package com.app.backend.services;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String s) {
        super(s);
    }
}
