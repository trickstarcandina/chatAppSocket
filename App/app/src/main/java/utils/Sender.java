package utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sender {
    private final Socket socket;
    private ObjectOutputStream oos = null;
    private DataOutputStream dos = null;

    public Sender(Socket socket) {
        this.socket = socket;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendString(String str) {
        try {
            dos.writeUTF(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void sendInt(int str) {
        try {
            dos.writeInt(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object object) {
        try {
            oos.writeObject(object);
            int a = 3;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
