package com.server.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UpdateUserRequest implements Serializable {
    static final long serialVersionUID = 9L;

    private Integer userId;
    private String address;
    private String username;
    private String nickName;
    private String password;
    private byte[] bytes;
    private String contentType;
}
