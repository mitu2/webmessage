package com.brageast.project.webmessage.exception;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String username) {
        super("用户" + username + "已存在");
    }
}
