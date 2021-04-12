package com.brageast.project.webmessage.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SimpleWebSocketHandler extends AbstractWebSocketHandler {



}
