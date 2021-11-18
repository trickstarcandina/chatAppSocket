import com.server.chat.model.LoginRequest;
import com.server.chat.model.Message;
import utils.Receiver;
import utils.Sender;

import java.io.IOException;
import java.net.Socket;

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
        TCPClient client = new TCPClient();
        client.connection();
        Sender sender = new Sender(client.mySocket);
        sender.sendObject(new LoginRequest("truong"));
        sender.sendString("truong");
        Receiver receiver = new Receiver(client.mySocket);
        while (true) {
            Message message = (Message) receiver.receiveObject();
            System.out.println("From " + message.getSender() + ": " + message.getContent());
        }
//        Message message = new Message("Test aaaaaa", "Truong");
//        sender.sendObject(message);
//        System.out.println("Create receiver");
//        Message receive = (Message) receiver.receiveObject();
//        System.out.println(receive.getContent() + " " + receive.getSender());
//        System.out.println("abcccc");
//        sender.sendString("aaaa day");
//        System.out.println(receiver.receiveString());
//        Student student = new Student("B18DCCN676", "Nguyễn Huy Trường", String.valueOf(client.mySocket.getLocalAddress()), 10);
//        sender.sendObject(student);
//        System.out.println(receiver.receiveInt());
//        sender.sendString(student.getHovaten());
//        System.out.println(receiver.receiveString());
//        sender.sendString(student.getMaSV());
//        System.out.println(receiver.receiveString());
//        sender.sendInt(student.getGroup());
//        System.out.println(receiver.receiveInt());
//        Answer answer = (Answer) receiver.receiveObject();
//        System.out.println(answer.getStudent().getMaSV());
//        sender.sendString("Answer");

    }
}
