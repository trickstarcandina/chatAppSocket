package com.server.chat.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    static final long serialVersionUID = 2L;
    private String username;
}
