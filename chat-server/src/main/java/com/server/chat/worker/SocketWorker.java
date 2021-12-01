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
    public static Map<Integer, UserSocket> userSocketMap = new HashMap<>();

    public void openConnection() {
        try {
            serverSocket = new ServerSocket(30601);
            System.out.println("Server is open");
            while (true) {
                socket = serverSocket.accept();
                new ClientWorker(socket, userService, messageRepository,
                        messagePendingRepository, conversationRepository,
                        userRepository).start();
            }
        } catch (IOException e) {
            log.error("Error " + e.getLocalizedMessage());
            closeConnection();
            openConnection();
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
