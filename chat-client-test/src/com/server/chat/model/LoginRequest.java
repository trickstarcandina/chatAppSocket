package com.server.chat.model;


import java.io.Serializable;

public class LoginRequest implements Serializable {
    static final long serialVersionUID = 2L;
    private int id;

    public LoginRequest(Integer id) {
        this.id = id;
    }
}
