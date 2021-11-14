package com.server.chat.controllers;

import com.server.chat.model.SocketServer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/socket")
@AllArgsConstructor
public class SocketController {

    private SocketServer socketServer;

    @PostMapping("/")
    public void openConnection() {
        socketServer.openConnection();
    }

    @GetMapping("/")
    public String getString() {
        return "abc";
    }
}
