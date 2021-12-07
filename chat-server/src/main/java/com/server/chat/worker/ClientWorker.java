package com.server.chat.worker;

import com.server.chat.model.Conversation;
import com.server.chat.model.CreateGroupRequest;
import com.server.chat.model.GetAllUserRequest;
import com.server.chat.model.LoginRequest;
import com.server.chat.model.Message;
import com.server.chat.model.MessagePending;
import com.server.chat.model.User;
import com.server.chat.model.UserSocket;
import com.server.chat.repositories.ConversationRepository;
import com.server.chat.repositories.MessagePendingRepository;
import com.server.chat.repositories.MessageRepository;
import com.server.chat.repositories.UserRepository;
import com.server.chat.services.MessageService;
import com.server.chat.services.UserService;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ClientWorker extends Thread {

    private final Socket socket;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataOutputStream dos;
    private final UserService userService;
    private final MessageService messageService;
    private final MessagePendingRepository messagePendingRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ClientWorker(Socket socket, UserService userService,
                        MessageService messageService,
                        MessagePendingRepository messagePendingRepository,
                        ConversationRepository conversationRepository,
                        UserRepository userRepository) {
        this.socket = socket;
        this.userService = userService;
        this.messageService = messageService;
        this.messagePendingRepository = messagePendingRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object object = this.ois.readObject();
                if (object instanceof LoginRequest) {
                    LoginRequest request = (LoginRequest) object;
                    log.info("Socket connect success with user id: " + request.getId());
                    sendPendingMessage(request);
                    SocketWorker.userSocketMap.put(request.getId(), new UserSocket(request.getId(), dis, ois, oos, dos));
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
                if (object instanceof GetAllUserRequest) {
                    GetAllUserRequest request = (GetAllUserRequest) object;
                    getAllUsers(request);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void receiveMessage(Message message) throws IOException {
        System.out.println(message.getContent() + " " + message.getUserId());
        message = messageService.save(message);
        List<User> users = userService.getUsersByConversationId(message.getConversationId());
        for (User user : users) {
            UserSocket to = SocketWorker.userSocketMap.get(user.getId());
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
            this.oos.writeObject(messagePending.getMessage());
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
            UserSocket to = SocketWorker.userSocketMap.get(userId);
            if (to != null) {
                to.getOos().writeObject(conversation);
            }
        }
    }

    private void getAllUsers(GetAllUserRequest request) throws IOException {
        List<User> users = userRepository.findAll().stream().map(user -> {
            user.setConversations(null);
            return user;
        }).collect(Collectors.toList());
        UserSocket to = SocketWorker.userSocketMap.get(request.getId());
        if (to != null) {
            to.getOos().writeObject(users);
        }
    }

}
