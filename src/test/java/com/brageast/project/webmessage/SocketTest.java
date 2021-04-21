package com.brageast.project.webmessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class SocketTest {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VybmFtZVwiOlwiMzI0MzI0MjM0MjM0XCJ9IiwiaWF0IjoxNjE2MjI2ODQ1LCJleHAiOjE2MTg4MTg4NDV9.iVkW2RLiG3ssSPDul5wU7WQcNlFYWH3Q29uKhfV3lmM";

    @Test
    void doSocket() {
        final OkHttpClient client = new OkHttpClient.Builder().build();
        final Thread thread = Thread.currentThread();
        final WebSocket webSocket = client.newWebSocket(new Request
                        .Builder()
                        .url("ws://localhost:8080/websocket")
                        .addHeader("Authorization", "Bearer " + TOKEN)
                        .build(),
                new WebSocketListener() {
                    @Override
                    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                        System.out.println("连接成攻");

                    }

                    @Override
                    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                        System.out.println("链接失败");
                        LockSupport.unpark(thread);
                    }
                }
        );
        LockSupport.park();
    }

    @Test
    void messageTypeTest() throws JsonProcessingException {
        double d = 25 / 2;
        System.out.println(d);
        System.out.println(25d /2);
    }


}
