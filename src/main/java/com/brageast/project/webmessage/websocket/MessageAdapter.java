package com.brageast.project.webmessage.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

public interface MessageAdapter {

    void doMessage(WebSocketSession session, Message.Sender senderMessage) throws IOException;

    Set<String> types();

}

