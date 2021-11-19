package com.server.chat.worker;

import com.server.chat.model.LoginRequest;
import com.server.chat.model.Message;
import com.server.chat.model.UserSocket;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketWorker {
    private ServerSocket serverSocket;
    private Socket socket;

    private DataInputStream dis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataOutputStream dos;
    private Map<Integer, UserSocket> users = new HashMap<>();

    public void openConnection() {
        try {
            serverSocket = new ServerSocket(30601);
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
                    System.out.println(request.getId());
                    users.put(request.getId(), new UserSocket(request.getId(), dis, ois, oos, dos));
                }
                else {
                    Message message = (Message) object;
//                    System.out.println(message.getContent() + " " + message.getSender());
//                    UserSocket to = users.get(message.getSendTo());
//                    to.getOos().writeObject(message);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
