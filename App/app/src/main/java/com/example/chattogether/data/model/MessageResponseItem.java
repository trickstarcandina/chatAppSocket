package com.example.chattogether.data.model;

public class MessageResponseItem {
    String content;
    int conversationId;
    String createdAt;
    int id;
    String updatedAt;
    int userId;
    private String url;
    private byte[] bytes;
    private String contentType;

    public MessageResponseItem(String content, int conversationId, String createdAt, int id, String updatedAt, String url, byte[] bytes, String contentType, int userId) {
        this.content = content;
        this.conversationId = conversationId;
        this.createdAt = createdAt;
        this.id = id;
        this.updatedAt = updatedAt;
        this.url = url;
        this.bytes = bytes;
        this.contentType = contentType;
        this.userId = userId;
    }

    public MessageResponseItem(String content, int conversationId, String createdAt, int id, String updatedAt, byte[] bytes, int userId) {
        this.content = content;
        this.conversationId = conversationId;
        this.createdAt = createdAt;
        this.id = id;
        this.updatedAt = updatedAt;
        this.bytes = bytes;
        this.userId = userId;
    }

    public MessageResponseItem(String content, int conversationId, String createdAt, int id, String updatedAt, int userId) {
        this.content = content;
        this.conversationId = conversationId;
        this.createdAt = createdAt;
        this.id = id;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}