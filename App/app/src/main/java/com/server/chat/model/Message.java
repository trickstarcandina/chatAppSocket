package com.server.chat.model;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Message implements Serializable {
    static final long serialVersionUID = 1L;
    private Integer id;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String content;
    private Integer userId;
    private Integer conversationId;

    public Message(String content, Integer userId, Integer conversationId) {
        this.content = content;
        this.userId = userId;
        this.conversationId = conversationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }
}
