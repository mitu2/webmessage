package com.brageast.project.webmessage.config.websocket;

import com.brageast.project.webmessage.util.WebSocketSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SimpleWebSocketHandler extends AbstractWebSocketHandler {

    public static final Set<WebSocketSession> WEB_SOCKET_SESSIONS = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        WEB_SOCKET_SESSIONS.add(session);
    }

    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        Object object = WebSocketSessionUtils.messageToObject(message, Object.class);

    }

    @Override
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        WEB_SOCKET_SESSIONS.remove(session);
    }
}
