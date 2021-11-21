package utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver {
    private final Socket socket;
    private DataInputStream dis;
    private ObjectInputStream ois;

    public Receiver(Socket socket) {
        this.socket = socket;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receiveObject() {
        if (socket != null && ois != null) {
            try {
                Object object = ois.readObject();
                if (object != null){
                    return object;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String receiveString() {
        if (socket != null && dis != null) {
            try {
                return dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Integer receiveInt() {
        if (socket != null && dis != null) {
            try {
                return dis.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
