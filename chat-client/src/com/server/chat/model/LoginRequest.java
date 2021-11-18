package com.server.chat.model;


import java.io.Serializable;

public class LoginRequest implements Serializable {
    static final long serialVersionUID = 2L;
    private String username;

    public LoginRequest(String username) {
        this.username = username;
    }
}
