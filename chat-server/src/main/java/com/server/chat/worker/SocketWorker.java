package com.server.chat.worker;

import com.server.chat.model.Conversation;
import com.server.chat.model.CreateGroupRequest;
import com.server.chat.model.LoginRequest;
import com.server.chat.model.Message;
import com.server.chat.model.MessagePending;
import com.server.chat.model.User;
import com.server.chat.model.UserSocket;
import com.server.chat.repositories.ConversationRepository;
import com.server.chat.repositories.MessagePendingRepository;
import com.server.chat.repositories.MessageRepository;
import com.server.chat.repositories.UserRepository;
import com.server.chat.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketWorker {

    private final UserService userService;
    private final MessageRepository messageRepository;
    private final MessagePendingRepository messagePendingRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataOutputStream dos;
    private Map<Integer, UserSocket> userSocketMap = new HashMap<>();

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
                System.out.println(object.toString());
                if (object instanceof LoginRequest) {
                    LoginRequest request = (LoginRequest) object;
                    log.info("Socket connect success with user id: " + request.getId());
                    userSocketMap.put(request.getId(), new UserSocket(request.getId(), dis, ois, oos, dos));
                    sendPendingMessage(request);
                }
                if (object instanceof CreateGroupRequest) {
                    CreateGroupRequest request = (CreateGroupRequest) object;
                    createConversation(request);
                    log.info("Create group success");
                }
                if (object instanceof Message) {
                    Message message = (Message) object;
                    receiveMessage(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error " + e.getLocalizedMessage());
            closeConnection();
            openConnection();
        }
    }

    private void receiveMessage(Message message) throws IOException {
        System.out.println(message.getContent() + " " + message.getUserId());
        message = messageRepository.save(message);
        List<User> users = userService.getUsersByConversationId(message.getConversationId());
        for (User user : users) {
            UserSocket to = userSocketMap.get(user.getId());
            if (to != null) {
                to.getOos().writeObject(message);
            }
            else {
                MessagePending messagePending = new MessagePending();
                messagePending.setUser(user);
                messagePending.setMessage(message);
                messagePendingRepository.save(messagePending);
            }
        }
    }

    private void sendPendingMessage(LoginRequest request) throws IOException {
        List<MessagePending> messagePendings = messagePendingRepository.findByUser(request.getId());
        for (MessagePending messagePending : messagePendings) {
            oos.writeObject(messagePending.getMessage());
        }
        messagePendingRepository.deleteAll(messagePendings);
    }

    private void createConversation(CreateGroupRequest request) throws IOException {
        Conversation conversation = new Conversation();
        conversation.setGroup(true);
        conversation.setName(request.getName());
        conversation.setUsers(userRepository.findAllById(request.getUserIds()));
        conversation = conversationRepository.save(conversation);
        for (Integer userId : request.getUserIds()) {
            UserSocket to = userSocketMap.get(userId);
            if (to != null) {
                to.getOos().writeObject(conversation);
            }
        }
    }

    public void closeConnection() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
