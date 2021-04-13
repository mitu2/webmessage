package com.brageast.project.webmessage.websocket;

import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.util.WebSocketSessionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Component
public class BroadcastMessage implements MessageAdapter {

    private static final Set<String> TYPES = Collections.singleton("Broadcast");


    @Override
    public void doMessage(WebSocketSession session, Message.Sender senderMessage) throws IOException {
        UserTable userTable = (UserTable) Objects.requireNonNull(WebSocketSessionUtils.getUser(session));
        for (WebSocketSession socketSession : WebSocketContext.getOnlineSessions()) {
            WebSocketSessionUtils.sendObject(socketSession, new Message.Recipient(userTable.getId(), "TEXT", senderMessage.getData()));
        }
    }

    @Override
    public Set<String> types() {
        return TYPES;
    }
}
