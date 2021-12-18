package com.server.chat.model;

import java.io.Serializable;

public class UpdateUserRequest implements Serializable {
    static final long serialVersionUID = 9L;
    private Integer userId;
    private String address;
    private String username;
    private String nickName;
    private String password;
    private byte[] bytes;
    private String contentType;

    public UpdateUserRequest(Integer userId, String address, String username, String nickName, String password, byte[] bytes, String contentType) {
        this.userId = userId;
        this.address = address;
        this.username = username;
        this.nickName = nickName;
        this.password = password;
        this.bytes = bytes;
        this.contentType = contentType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}