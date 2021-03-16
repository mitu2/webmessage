package com.brageast.project.webmessage.exception;

/**
 * 用户已存在
 * @author chenmoand
 */
public class UserExistedException extends RuntimeException {

    public UserExistedException(String username) {
        super("用户" + username + "已存在");
    }
}
