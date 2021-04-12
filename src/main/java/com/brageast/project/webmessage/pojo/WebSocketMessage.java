package com.brageast.project.webmessage.pojo;

import com.brageast.project.webmessage.constant.MessageType;
import com.brageast.project.webmessage.pojo.table.UserTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Deprecated
public class WebSocketMessage {

    private User recipient;

    private User sender;

    /**
     * 发送消息类型
     */
    @Builder.Default
    private MessageType type = MessageType.TEXT;

    private String message;

    private Object data;

    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    public WebSocketMessage to(User sender) {
        return WebSocketMessage
                .builder()
                .timestamp(timestamp)
                .type(type)
                .message(message)
                .data(data)
                .sender(sender)
                .build();
    }

}
