package com.server.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Getter
@Setter
public class UserSocket {
    private int id;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataOutputStream dos;

    public UserSocket() {
    }

    public UserSocket(int id, DataInputStream dis, ObjectInputStream ois, ObjectOutputStream oos, DataOutputStream dos) {
        this.id = id;
        this.dis = dis;
        this.ois = ois;
        this.oos = oos;
        this.dos = dos;
    }
}
