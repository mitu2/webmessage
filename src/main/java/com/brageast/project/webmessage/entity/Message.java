package com.brageast.project.webmessage.entity;

import com.brageast.project.webmessage.constant.MessageType;
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

    /**
     * 发送者
     */
    private User from;
    /**
     * 接收者
     */
    private User to;
    /**
     * 发送消息类型
     */
    @Builder.Default
    private  MessageType type = MessageType.TEXT;

    private Object data;

    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
}
