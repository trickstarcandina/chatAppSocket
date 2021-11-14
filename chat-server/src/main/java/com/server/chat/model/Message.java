package com.server.chat.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    static final long serialVersionUID = 1L;
    private String content;
    private String sender;
    private String sendTo;

    public Message(String content, String sender, String sendTo) {
        this.content = content;
        this.sender = sender;
        this.sendTo = sendTo;
    }
}
