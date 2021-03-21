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
public class Message {

    private UserTable user;

    /**
     * 发送消息类型
     */
    @Builder.Default
    private MessageType type = MessageType.TEXT;

    private Object data;

    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    public Message to(UserTable user) {
        return Message
                .builder()
                .timestamp(timestamp)
                .type(type)
                .data(data)
                .user(user)
                .build();
    }

}
