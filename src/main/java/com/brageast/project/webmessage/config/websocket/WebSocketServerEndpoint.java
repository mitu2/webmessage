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
                .data("链接成功")
                .build());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }

    @OnMessage
    public void onMessage(Session session, String text) {
        try {
            final Message message = mapper.readValue(text, Message.class);
            final UserTable user = message.getUser();
            if (user == null) {
                WebSocketUtils.sendObject(session, Message
                        .builder()
                        .data("请指定发送用户")
                        .type(MessageType.ERROR)
                        .build()
                );
                return;
            }
            final Message toMessage = message.to(WebSocketUtils.getUserTable(session));
            for (Session openSession : session.getOpenSessions()) {
                final UserTable userTable = WebSocketUtils.getUserTable(openSession);
                if (userTable != null || openSession == session && Objects.equals(userTable.getId(), user.getId())) {
                    WebSocketUtils.sendObject(openSession, toMessage);
                }
            }
        } catch (JsonProcessingException e) {
//            session.getBasicRemote().sendText(Message.builder());
        }
    }


}
