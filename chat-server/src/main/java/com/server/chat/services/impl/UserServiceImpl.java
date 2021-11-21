package com.server.chat.services.impl;

import com.server.chat.dto.MyUserDetails;
import com.server.chat.model.Conversation;
import com.server.chat.model.User;
import com.server.chat.repositories.ConversationRepository;
import com.server.chat.repositories.UserRepository;
import com.server.chat.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
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

    @Override
    public List<User> getUsersByConversationId(Integer conversationId) {
        return userRepository.findUsersByConversationId(conversationId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Not found username : " + username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new MyUserDetails(user.getUsername(), user.getPassword(), true, true,
                true, true , authorities, user);
    }
}
