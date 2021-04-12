package com.brageast.project.webmessage.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.lang.reflect.Method;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSocketConfig implements WebSocketConfigurer {

    private final SimpleWebSocketHandler simpleWebSocketHandler;

//    @Bean
//    ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }


    @SneakyThrows
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        SockJsServiceRegistration sockJS = registry.addHandler(simpleWebSocketHandler, "/web-socket")
                //NOTE: 似乎不让用通配符 * 而新版Spring也没开放setAllowedOriginPatterns使用权限
                .setAllowedOrigins("http://localhost:8090")
                .withSockJS();
//        Method method = sockJS.getClass().getDeclaredMethod("setAllowedOriginPatterns", String[].class);
//        method.setAccessible(true);
//        method.invoke(sockJS, (Object) new String[]{"*"});
    }
}
