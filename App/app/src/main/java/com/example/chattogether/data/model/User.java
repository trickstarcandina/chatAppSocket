package com.example.chattogether.data.model;

import java.util.ArrayList;

public class User {
    private int id;
    private String createdAt;
    private String updatedAt;
    private String username;
    private String password;
    private String nickName = null;
    private String address;
    private boolean status;
    public ArrayList< Conversation > conversations = new ArrayList <> ();

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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAddress() {
        return address;
    }

    public boolean getStatus() {
        return status;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}



