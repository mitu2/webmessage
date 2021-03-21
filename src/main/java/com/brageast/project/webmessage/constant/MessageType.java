package com.brageast.project.webmessage.constant;

public enum MessageType {

    TEXT,
    ERROR,
    OTHER;

    @Override
    public String toString() {
        return this.name();
    }
}
