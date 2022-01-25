import com.server.chat.model.Conversation;
import com.server.chat.model.CreateGroupRequest;
import com.server.chat.model.GetAllUserRequest;
import com.server.chat.model.LoginRequest;
import com.server.chat.model.User;
import utils.Receiver;
import utils.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class GetAllUserTest {
    Socket mySocket = null;

    public void connection() {
        try {
            mySocket = new Socket("localhost", 30601);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        GetAllUserTest client = new GetAllUserTest();
        client.connection();
        Sender sender = new Sender(client.mySocket);
        sender.sendObject(new LoginRequest(1));
        Receiver receiver = new Receiver(client.mySocket);
        Thread thread = new Thread(() -> {
            while (true) {
                List<User> users = (List<User>) receiver.receiveObject();
                for (User user : users) {
                    System.out.println(user.getId());
                }
            }
        });
        Thread thread1 = new Thread(() -> {
            GetAllUserRequest request = new GetAllUserRequest(1);
            sender.sendObject(request);
        });
        thread.start();
        thread1.start();
    }
}
