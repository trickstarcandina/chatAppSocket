package com.server.chat.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Getter
@Setter
public class User {
    private String username;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataOutputStream dos;

    public User() {
    }

    public User(String username, DataInputStream dis, ObjectInputStream ois, ObjectOutputStream oos, DataOutputStream dos) {
        this.username = username;
        this.dis = dis;
        this.ois = ois;
        this.oos = oos;
        this.dos = dos;
    }
}
