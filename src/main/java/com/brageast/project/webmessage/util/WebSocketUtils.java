package com.brageast.project.webmessage.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketUtils {

    public static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 忽略为null的字段序列化
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private WebSocketUtils() {

    }

    public static void sendObject() {

    }

}
