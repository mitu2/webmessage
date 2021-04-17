package com.brageast.project.webmessage.constant;

public interface MessageType {

    String BROADCAST = "Broadcast";


    // 客户端处理类型
    /**
     * 表示多个用户同时登录一个常昊错误
     */
    String USER_LOGINS = "USER_LOGINS";

    /**
     * 格式不正确
     */
    String INCORRECT_FORMAT = "INCORRECT_FORMAT";

}
