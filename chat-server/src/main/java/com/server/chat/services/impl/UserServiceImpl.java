package com.server.chat.services.impl;

import com.server.chat.core.response.BaseResponse;
import com.server.chat.core.response.Response;
import com.server.chat.core.response.ResponseBuilder;
import com.server.chat.dto.MyUserDetails;
import com.server.chat.model.Conversation;
import com.server.chat.model.UpdateUserRequest;
import com.server.chat.model.User;
import com.server.chat.repositories.ConversationRepository;
import com.server.chat.repositories.UserRepository;
import com.server.chat.services.MinioService;
import com.server.chat.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private static final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replaceAll("-", "");
    private static final String folder = "/"+ time + "/";

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final BCryptPasswordEncoder encoder;
    private final MinioService minioService;

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

    @Override
    public User updateUser(UpdateUserRequest request) {
        if (request.getUserId() == null)
            return null;
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user != null) {
            if (request.getAddress() != null)
                user.setAddress(request.getAddress());
            if (request.getUsername() != null)
                user.setUsername(request.getUsername());
            if (request.getNickName() != null)
                user.setNickName(request.getNickName());
            if (request.getPassword() != null) {
                user.setPassword(encoder.encode(request.getPassword()));
            }
            if (request.getBytes() != null && request.getContentType() != null) {
                String name = request.getUserId() +"-"+ LocalDateTime.now() + "." + request.getContentType();
                minioService.upload(folder, name, new ByteArrayInputStream(request.getBytes()));
                String url = folder + name;
                user.setAvatarUrl(url);
                System.out.println("update avatar success");
            }
            return userRepository.save(user);
        }
        return null;
    }
}
