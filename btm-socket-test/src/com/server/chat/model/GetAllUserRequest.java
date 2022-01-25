package com.server.chat.model;

import java.io.Serializable;

public class GetAllUserRequest implements Serializable {

    static final long serialVersionUID = 7L;
    private int id;

    public GetAllUserRequest() {
    }

    public GetAllUserRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}