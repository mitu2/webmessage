package com.brageast.project.webmessage.constant;

public enum MessageType {
    TEXT,
    ERROR,
    INFO,
    ALL_USER,
    OTHER;

    @Override
    public String toString() {
        return this.name();
    }
}
