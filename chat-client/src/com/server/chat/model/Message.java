package com.server.chat.model;

import java.io.Serializable;

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

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
