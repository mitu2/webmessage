package com.brageast.project.webmessage.util;

import com.brageast.project.webmessage.config.security.CustomizeUserDetails;
import com.brageast.project.webmessage.pojo.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class WebSocketSessionUtils {

    public static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 忽略为null的字段序列化
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private WebSocketSessionUtils() {
    }

    /**
     * 得到传入Session的用户认证信息
     *
     * @param session WebSocket会话
     * @return 用户认证信息
     */
    public static User getSessionUser(WebSocketSession session) {
        Objects.requireNonNull(session, "session must not null!");
        if (session.isOpen() && session.getPrincipal() instanceof Authentication) {
            Authentication authentication = (Authentication) session.getPrincipal();
            if (authentication.getPrincipal() instanceof CustomizeUserDetails) {
                return ((CustomizeUserDetails) authentication.getPrincipal()).getUserTable();
            }
        }
        return null;
    }


    public static void sendText(WebSocketSession session, String text) throws IOException {
        Objects.requireNonNull(session, "session must not null!");
        Objects.requireNonNull(text, "text must not null!");
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(text));
        }
    }

    public static void sendObject(WebSocketSession session, Object object) throws IOException {
        Objects.requireNonNull(session, "session must not null!");
        Objects.requireNonNull(object, "object must not null!");
        String text = MAPPER.writeValueAsString(object);
        sendText(session, text);
    }

    public static String messageToString(TextMessage message) {
        Objects.requireNonNull(message, "message must not null!");
        byte[] bytes = message.asBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static <T> T messageToObject(TextMessage message, Class<T> cls) throws IOException {
        Objects.requireNonNull(message, "message must not null!");
        byte[] bytes = message.asBytes();
        return MAPPER.readValue(bytes, cls);
    }


}
