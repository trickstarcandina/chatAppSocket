package com.example.chattogether.data.model;

import java.util.ArrayList;

public class UserResponse {
    public ArrayList<Conversation> conversations;
    private int id;
    private String createdAt;
    private String updatedAt;
    private String username;
    private String password;
    private String nickName = null;
    private String avatarUrl;
    private String address;
    private boolean status;

    public UserResponse(int id, String createdAt, String updatedAt, String username, String password, String nickName, String avatarUrl, String address, boolean status, ArrayList<Conversation> conversations) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.status = status;
        this.conversations = conversations;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
    }

    // Getter Methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Setter Methods

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}



