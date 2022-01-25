package com.server.chat.services;

import com.server.chat.core.response.Response;
import com.server.chat.model.UpdateUserRequest;
import com.server.chat.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Response create(User user);
    List<User> getUsersByConversationId(Integer conversationId);
    String updateInforUser(User user);
    User updateUser(UpdateUserRequest request);
}
