package com.server.chat.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Conversation implements Serializable {
    static final long serialVersionUID = 3L;
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private boolean isGroup;

    public Conversation() {
    }

    public Conversation(Integer id, LocalDateTime createdAt, String name, boolean isGroup, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.isGroup = isGroup;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
