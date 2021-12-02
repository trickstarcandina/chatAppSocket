package com.example.chattogether.data.model;

public class Conversation{
    private int id;
    private String createdAt;
    private String updatedAt;
    private String name = null;
    private boolean isGroup;


    // Getter Methods

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public boolean getIsGroup() {
        return isGroup;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }
}