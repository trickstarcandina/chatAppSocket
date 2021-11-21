package com.server.chat.controllers;

import com.server.chat.dto.MyUserDetails;
import com.server.chat.model.User;
import com.server.chat.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.create(user));
    }

    @RequestMapping(value = "updateInfoUser", method = RequestMethod.POST)
    public ResponseEntity<User> updateInfoUser(@RequestBody User user) {
        // Thắng đang làm cập nhật thông tin user này nhưng chưa xong
        // mng ko làm trùng của tôi nhé :))
        return null;
    }
}
