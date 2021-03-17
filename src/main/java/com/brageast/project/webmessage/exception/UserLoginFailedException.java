package com.brageast.project.webmessage.exception;

public class UserLoginFailedException extends RuntimeException {
    public UserLoginFailedException(String msg) {
        super(msg);
    }
}
