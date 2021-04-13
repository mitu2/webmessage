package com.brageast.project.webmessage.websocket;

import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.util.WebSocketSessionUtils;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class WebSocketContext {

    private static final Set<WebSocketSession> WEB_SOCKET_SESSIONS = Collections.synchronizedSet(new HashSet<>());

    public static void closeSession(WebSocketSession session) throws IOException {
        Objects.requireNonNull(session, "session must not null!");
        WEB_SOCKET_SESSIONS.remove(session);
        if (session.isOpen()) {
            session.close();
        }
    }

    public static void addSession(WebSocketSession session) {
        Objects.requireNonNull(session, "session must not null!");
        WEB_SOCKET_SESSIONS.add(session);
    }

    public static void removeSession(WebSocketSession session) {
        Objects.requireNonNull(session, "session must not null!");
        WEB_SOCKET_SESSIONS.remove(session);
    }

    public static Set<WebSocketSession> getSessionByUserId(long id) {
        Set<WebSocketSession> result = new HashSet<>();
        for (WebSocketSession session : WEB_SOCKET_SESSIONS) {
            UserTable user = (UserTable) WebSocketSessionUtils.getUser(session);
            if (user != null && Objects.equals(user.getId(), id)) {
                result.add(session);
            }
        }
        return result;
    }

    public static Set<WebSocketSession> getOnlineSessions() {
        return WEB_SOCKET_SESSIONS.stream()
                                    .filter(WebSocketSession::isOpen)
                                    .collect(Collectors.toSet());
    }

}
