package com.server.chat.services.impl;

import com.server.chat.model.Conversation;
import com.server.chat.model.User;
import com.server.chat.repositories.ConversationRepository;
import com.server.chat.repositories.UserRepository;
import com.server.chat.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    @Override
    public User create(User user) {
        user = userRepository.save(user);
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (!u.getId().equals(user.getId())) {
                Conversation conversation = new Conversation();
                conversation.setUsers(Arrays.asList(user, u));
                conversationRepository.save(conversation);
            }
        }
        return user;
    }
}
