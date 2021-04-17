package com.brageast.project.webmessage.websocket;

import com.brageast.project.webmessage.constant.MessageType;
import com.brageast.project.webmessage.pojo.table.UserTable;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SimpleWebSocketHandler extends AbstractWebSocketHandler {

    private final List<MessageAdapter> adapters;

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        try {
            UserTable userTable = (UserTable) Objects.requireNonNull(WebSocketSessionUtils.getUser(session));
            Set<WebSocketSession> sessions = WebSocketContext.getSessionByUserId(userTable.getId());
            // 判断是否存在多个相同的用户
            if (!sessions.isEmpty()) {
                for (WebSocketSession ws : sessions) {
                    WebSocketSessionUtils.sendObject(ws, new Message.Error(MessageType.USER_LOGINS, "异地登录账号", null));
                    WebSocketContext.removeSession(ws);
                }
            }
        } catch (Exception ignored) {

        } finally {
            WebSocketContext.addSession(session);
        }
    }

    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        Message.Sender sender;
        try {
            sender = WebSocketSessionUtils.messageToObject(message, Message.Sender.class);
        } catch (IOException e) {
            WebSocketSessionUtils.sendObject(session, new Message.Error(MessageType.INCORRECT_FORMAT, "请按照格式发送信息", null));
            return;
        }

        for (MessageAdapter adapter : adapters) {
            if (adapter.types().contains(sender.getType())) {
                adapter.doMessage(session, sender);
            }
        }
    }

    @Override
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        WebSocketContext.removeSession(session);

    }
}
