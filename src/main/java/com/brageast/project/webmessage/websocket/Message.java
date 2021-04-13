package com.brageast.project.webmessage.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface Message {

    Sender EMPTY_SENDER = new Sender(null, "EMPTY", null);
    Recipient EMPTY_RECIPIENT = new Recipient(null, "EMPTY", null);

    String getType();

    Object getData();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Sender implements Message {
        private Long rid;
        private String type;
        private Object data;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Recipient implements Message {
        private Long sid;
        private String type;
        private Object data;
    }



}
