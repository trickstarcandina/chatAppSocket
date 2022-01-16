package com.example.chattogether.ui.service.connection;

import com.example.chattogether.ui.home.HomeViewModel;
import com.example.chattogether.ui.service.OnConnected;
import com.server.chat.model.LoginRequest;
import com.server.chat.model.Message;
import com.example.chattogether.data.model.UserResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import utils.Receiver;
import utils.Sender;


public class TCPClient {
    private static TCPClient tcpClient;
    public static Socket mySocket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    DataOutputStream os = null;
    DataInputStream is = null;
    public static String ip;
    public static Sender sender;
    public static Receiver receiver;
    public static UserResponse userResponse;
    HomeViewModel homeViewModel;


    public static TCPClient getInstance(){
        if(tcpClient == null){
            tcpClient = new TCPClient();
        }
        return tcpClient;
    }

    public Socket getMySocket(){
        return mySocket;
    }

    public void connection(OnConnected onConnected) { // kết nối tới server
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mySocket = new Socket("34.122.94.78", 30601);
                    int a = 3;
                    onConnected.onConnectedSuccessfully();

//            oos = new ObjectOutputStream(mySocket.getOutputStream());
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }).start();
    }
    public void sendString(String str) { // gửi dữ liệu kiểu String đến server 
        if (mySocket != null && os != null) {
            try {
                os.writeUTF(str);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public String receiveString() { // nhận dữ liệu kiểu String từ server
        if (mySocket != null && is != null) {
            try {
                String responseStr;
                if ((responseStr = is.readUTF()) != null) {
                    return responseStr;
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return null;
    }

    public void login(LoginRequest s) { // gửi dữ liệu kiểu Student đến server
        try {
            oos.writeObject(s);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void sendMessage(Message s) { // gửi dữ liệu kiểu Student đến server
        try {
            oos.writeObject(s);
            int a = 10;
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void sendNumber(int a) { // gửi dữ liệu kiểu int đến server
        if (mySocket != null && os != null) {
            try {
                os.writeInt(a);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public int receiveNumber() { // nhận dữ liệu kiểu int từ server
        if (mySocket != null && is != null) {
            try {
                int a;
                if ((a = is.readInt()) != -1) {
                    return a;
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return 0;
    }


    public void close() { // ngắt kết nối tới server
        if (mySocket != null && oos != null && ois != null && os != null && is != null) {
            try {
                oos.close();
                ois.close();
                os.close();
                is.close();
                mySocket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

//    public static void main(String[] args) {
//        TCPClient client = new TCPClient();
//        client.connection();
//
//        client.close();
//    }
public Sender getSender() {
    return sender;
}

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public static UserResponse getUser() {
        return userResponse;
    }

    public static void setUser(UserResponse userResponse) {
        TCPClient.userResponse = userResponse;
    }


}