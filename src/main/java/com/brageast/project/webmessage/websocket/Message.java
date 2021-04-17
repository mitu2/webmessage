package com.brageast.project.webmessage.websocket;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

public interface Message {

    Sender EMPTY_SENDER = new Sender(null, "EMPTY", null);
    Recipient EMPTY_RECIPIENT = new Recipient(null, "EMPTY", null);

    String getType();

    Object getData();

    Timestamp getTimestamp();

    default String getChatType() {
        return getClass().getSimpleName();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Sender implements Message {
        private Long rid;
        private String type;
        private Object data;
        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
        private Timestamp timestamp;

        public Sender(Long rid, String type, Object data) {
            this.rid = rid;
            this.type = type;
            this.data = data;
            this.timestamp = Timestamp.from(Instant.now());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Recipient implements Message {
        private Long sid;
        private String type;
        private Object data;
        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
        private Timestamp timestamp;

        public Recipient(Long sid, String type, Object data) {
            this.sid = sid;
            this.type = type;
            this.data = data;
            this.timestamp = Timestamp.from(Instant.now());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Error implements Message {
        private String type;
        private String message;
        private Object data;
        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
        private Timestamp timestamp;

        public Error(String type, String message, Object data) {
            this.type = type;
            this.message = message;
            this.data = data;
        }

    }


}
