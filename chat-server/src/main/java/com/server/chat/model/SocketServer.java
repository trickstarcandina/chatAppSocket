package com.server.chat.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketServer {
    private ServerSocket serverSocket;
    private Socket socket;

    private DataInputStream dis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataOutputStream dos;
    private Map<String, User> users = new HashMap<>();

    public void openConnection() {
        try {
            serverSocket = new ServerSocket(9090);
            System.out.println("Server is open");
            while (true) {
                socket = serverSocket.accept();
                ois = new ObjectInputStream(socket.getInputStream());
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                Object object = ois.readObject();
                if (object instanceof LoginRequest) {
                    LoginRequest request = (LoginRequest) object;
                    System.out.println(request.getUsername());
                    users.put(request.getUsername(), new User(request.getUsername(), dis, ois, oos, dos));
                }
                else {
                    Message message = (Message) object;
                    System.out.println(message.getContent() + " " + message.getSender());
                    User to = users.get(message.getSendTo());
                    to.getOos().writeObject(message);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
