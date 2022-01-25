import com.server.chat.model.LoginRequest;
import com.server.chat.model.Message;
import utils.Receiver;
import utils.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    Socket mySocket = null;

    public void connection() {
        try {
            mySocket = new Socket("34.122.94.78", 30601);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TCPClient client = new TCPClient();
        client.connection();
        Sender sender = new Sender(client.mySocket);
        sender.sendObject(new LoginRequest(1));
        Receiver receiver = new Receiver(client.mySocket);
        Thread thread = new Thread(() -> {
            while (true) {
                Message message = (Message) receiver.receiveObject();
                System.out.println("From " + message.getUserId() + ": " + message.getContent());
            }
        });
        Thread thread1 = new Thread(() -> {
            while (true) {
                String content = scanner.nextLine();
                Message message = new Message(content, 1, 1);
                sender.sendObject(message);
            }
        });
        thread.start();
        thread1.start();
    }
}
