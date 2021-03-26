package com.brageast.project.webmessage.config.websocket;

import com.brageast.project.webmessage.constant.MessageType;
import com.brageast.project.webmessage.pojo.Message;
import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.util.WebSocketUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Objects;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketServerEndpoint {

    final ObjectMapper mapper;

    @Autowired
    public WebSocketServerEndpoint() {
        mapper = WebSocketUtils.MAPPER;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        WebSocketUtils.sendObject(session, Message
                .builder()
                .type(MessageType.INFO)
                .data("链接成功")
                .build()
        );
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }

    @OnMessage
    public void onMessage(Session session, String text) {
        final Message recipientMessage;
        try {
            recipientMessage = mapper.readValue(text, Message.class);
        } catch (JsonProcessingException e) {
            // 发送的格式不符合
            WebSocketUtils.sendObject(session, Message
                    .builder()
                    .message("发送格式不正确")
                    .type(MessageType.ERROR)
                    .build()
            );
            return;
        }
        if (recipientMessage.getType() == null) {
            WebSocketUtils.sendObject(session, Message
                    .builder()
                    .message("请指定发送类型")
                    .data(MessageType.values())
                    .type(MessageType.ERROR)
                    .build()
            );
            return;
        }
        final UserTable recipient = recipientMessage.getRecipient();
        final Message senderMessage = recipientMessage.to(WebSocketUtils.getUserTable(session));
        switch (recipientMessage.getType()) {
            case ALL_USER:
                senderMessage.setType(MessageType.TEXT);
                for (Session openSession : session.getOpenSessions()) {
                    if (openSession != session) {
                        WebSocketUtils.sendObject(openSession, senderMessage);
                    }
                }
                break;
            case TEXT:
                if (recipient == null) {
                    WebSocketUtils.sendObject(session, Message
                            .builder()
                            .message("请指定发送用户对象")
                            .type(MessageType.ERROR)
                            .build()
                    );
                    break;
                }
                for (Session openSession : session.getOpenSessions()) {
                    final UserTable userTable = WebSocketUtils.getUserTable(openSession);
                    if (userTable != null && openSession != session && Objects.equals(userTable.getId(), recipient.getId())) {
                        WebSocketUtils.sendObject(openSession, senderMessage);
                    }
                }
                break;
            default:
                // TODO 暂时未有其他判断
        }


    }


}
