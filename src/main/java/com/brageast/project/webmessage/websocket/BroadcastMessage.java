package com.brageast.project.webmessage.websocket;

import com.brageast.project.webmessage.constant.MessageType;
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

    private static final Set<String> TYPES = Collections.singleton(MessageType.BROADCAST);

    @Override
    public void doMessage(WebSocketSession session, Message.Sender senderMessage) throws IOException {
        UserTable userTable = (UserTable) Objects.requireNonNull(WebSocketSessionUtils.getUser(session));
        Message.Recipient recipient = new Message.Recipient(userTable.getId(), MessageType.BROADCAST, senderMessage.getData(), senderMessage.getTimestamp());
        for (WebSocketSession socketSession : WebSocketContext.getOnlineSessions()) {
            if (session == socketSession) {
                // 啥也不做
                continue;
            }
            WebSocketSessionUtils.sendObject(socketSession, recipient);
        }
    }

    @Override
    public Set<String> types() {
        return TYPES;
    }
}
