package com.server.chat.services;

import com.server.chat.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User create(User user);
    List<User> getUsersByConversationId(Integer conversationId);
}
