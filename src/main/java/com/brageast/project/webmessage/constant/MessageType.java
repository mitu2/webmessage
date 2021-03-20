package com.brageast.project.webmessage.constant;

import lombok.ToString;

public enum MessageType {

    TEXT,
    ERROR,
    OTHER;

    @Override
    public String toString() {
        return this.name();
    }
}
