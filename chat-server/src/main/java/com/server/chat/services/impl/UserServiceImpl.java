package com.server.chat.services.impl;

import com.server.chat.core.response.BaseResponse;
import com.server.chat.core.response.Response;
import com.server.chat.core.response.ResponseBuilder;
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

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
    public Response create(User user) {
        if(userRepository.checkUserNameExists(user.getUsername()) > 0){
            return ResponseBuilder.ok(201,"Username đã tồn tại, vui lòng chọn một username khác");
        } else {
            user = userRepository.save(user);
        }
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (!u.getId().equals(user.getId())) {
                Conversation conversation = new Conversation();
                conversation.setUsers(Arrays.asList(user, u));
                conversationRepository.save(conversation);
            }
        }
        return ResponseBuilder.ok(user);
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

    @Override
    public String updateInforUser(User user){
        userRepository.updateUser(user.getAddress(), user.getNickName(), user.getPassword(), user.getUsername());
        return "Cập nhật thông tin tài khoản thành công";
    }
}
