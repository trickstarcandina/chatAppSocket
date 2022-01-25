package com.server.chat.controllers;

import com.server.chat.common.utils.JwtUtils;
import com.server.chat.dto.AuthenticationRequest;
import com.server.chat.dto.MyUserDetails;
import com.server.chat.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username " + request.getUsername());
        }
        MyUserDetails myUserDetails = (MyUserDetails) userService.loadUserByUsername(request.getUsername());
        String jwt = JwtUtils.generateToken(myUserDetails);
        return ResponseEntity.ok(jwt);
    }
}
