package com.server.chat.controllers;

import com.server.chat.dto.MyUserDetails;
import com.server.chat.core.response.ResponseBuilder;
import com.server.chat.model.User;
import com.server.chat.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private BCryptPasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<User> getUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(myUserDetails.getUser());
    }

    @GetMapping("/conversation")
    private ResponseEntity<List<User>> getUsersByConversation(@RequestParam("conversationId") Integer conversationId) {
        return ResponseEntity.ok(userService.getUsersByConversationId(conversationId));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.create(user).build());
    }

    @RequestMapping(value = "updateInfoUser", method = RequestMethod.POST)
    public ResponseEntity updateInfoUser(@RequestBody User user) {
        if(user.getPassword() != null) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        return ResponseEntity.ok(ResponseBuilder.ok(200, userService.updateInforUser(user)).build());
    }
}
