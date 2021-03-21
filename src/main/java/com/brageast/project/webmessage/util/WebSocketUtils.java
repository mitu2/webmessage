package com.brageast.project.webmessage.util;

import com.brageast.project.webmessage.pojo.table.UserTable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;

import javax.websocket.Session;

public class WebSocketUtils {

    public static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 忽略为null的字段序列化
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private WebSocketUtils() {

    }

    public static UserTable getUserTable(Session session) {
        if (session != null && session.getUserPrincipal() instanceof Authentication) {
            Authentication authentication = (Authentication) session.getUserPrincipal();
            if (authentication.getPrincipal() instanceof UserTable) {
                return (UserTable) authentication.getPrincipal();
            }
        }
        return null;
    }

    public static boolean sendObject(Session session, Object object) {
        return sendObject(session, object, false);
    }

    public static boolean sendObject(Session session, Object object, boolean async) {
        if (session == null || !session.isOpen() || object == null) {
            return false;
        }
        boolean isSuss = false;
        try {
            final String json = MAPPER.writeValueAsString(object);
            if (async) {
                session.getAsyncRemote().sendText(json);
            } else {
                session.getBasicRemote().sendText(json);
            }
            isSuss = true;
        } catch (Exception e) {
            // 什么也不做
        }
        return isSuss;
    }

}
