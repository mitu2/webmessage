package com.brageast.project.webmessage.config.websocket;

import com.brageast.project.webmessage.websocket.SimpleWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSocketConfig implements WebSocketConfigurer {

    @Value("${spring.profiles.active}")
    private String active;

    private final SimpleWebSocketHandler simpleWebSocketHandler;

//    @Bean
//    ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }


    @SneakyThrows
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WebSocketHandlerRegistration webSocketHandlerRegistration = registry.addHandler(simpleWebSocketHandler, "/web-socket");
        if ("prod".equalsIgnoreCase(active)) {
            webSocketHandlerRegistration.setAllowedOrigins("http://localhost:8090");

        }
        SockJsServiceRegistration sockJS = webSocketHandlerRegistration
                //NOTE: 似乎不让用通配符 * 而新版Spring也没开放setAllowedOriginPatterns使用权限
                .withSockJS();
//        Method method = sockJS.getClass().getDeclaredMethod("setAllowedOriginPatterns", String[].class);
//        method.setAccessible(true);
//        method.invoke(sockJS, (Object) new String[]{"*"});
    }
}
