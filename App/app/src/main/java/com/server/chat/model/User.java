package com.server.chat.model;

import com.example.chattogether.data.model.Conversation;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class User implements Serializable {
    static final long serialVersionUID = 5L;
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String username;
    private String password;
    private String nickName;
    private String address;
    private boolean status;
    private String avatarUrl;
    private List<Conversation> conversations;

    public User(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String username, String password, String nickName, String address, boolean status, String avatarUrl, List<Conversation> conversations) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.address = address;
        this.status = status;
        this.avatarUrl = avatarUrl;
        this.conversations = conversations;
    }

    public User(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String username, String password, String nickName, String address, boolean status, List<Conversation> conversations) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.address = address;
        this.status = status;
        this.conversations = conversations;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}