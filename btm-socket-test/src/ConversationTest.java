import com.server.chat.model.Conversation;
import com.server.chat.model.CreateGroupRequest;
import com.server.chat.model.LoginRequest;
import utils.Receiver;
import utils.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ConversationTest {
    Socket mySocket = null;

    public void connection() {
        try {
            mySocket = new Socket("localhost", 30601);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        ConversationTest client = new ConversationTest();
        client.connection();
        Sender sender = new Sender(client.mySocket);
        sender.sendObject(new LoginRequest(1));
        Receiver receiver = new Receiver(client.mySocket);
        Thread thread = new Thread(() -> {
            while (true) {
                Conversation conversation = (Conversation) receiver.receiveObject();
                System.out.println(conversation.toString());
            }
        });
        Thread thread1 = new Thread(() -> {
            CreateGroupRequest request = new CreateGroupRequest("Test", Arrays.asList(1,2,3,4,5));
            client.connection();
            Sender newSender = new Sender(client.mySocket);
            newSender.sendObject(request);
        });
        thread.start();
        thread1.start();
    }
}
