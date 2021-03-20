package com.brageast.project.webmessage.config.websocket;

import com.brageast.project.webmessage.entity.Message;
import com.brageast.project.webmessage.util.WebSocketUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@Component
@ServerEndpoint("/websocket")
public class WebSocketServerEndpoint {

    final ObjectMapper mapper;

    @Autowired
    public WebSocketServerEndpoint() {
        mapper = WebSocketUtils.MAPPER;
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info(session.getUserPrincipal().toString());
    }

    @OnClose
    public void OnClose(Session session) {
        log.info(session.getUserPrincipal().toString());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info(session.getUserPrincipal().toString());
    }

    @OnMessage
    public void onMessage(Session session, String text) {
        try {
            final Message message = mapper.readValue(text, Message.class);
        } catch (JsonProcessingException e) {
//            session.getBasicRemote().sendText(Message.builder());
        }
    }


}
