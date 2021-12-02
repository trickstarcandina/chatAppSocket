package com.server.chat.model;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    static final long serialVersionUID = 2L;
    private int id;

    public LoginRequest(Integer id) {
        this.id = id;
    }

//    public LoginRequest(int id) {
//        this.id = id;
//    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}