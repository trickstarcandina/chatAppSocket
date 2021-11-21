package com.server.chat.controllers;

import com.server.chat.worker.SocketWorker;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/socket")
@AllArgsConstructor
public class SocketController {

    private SocketWorker socketServer;

    @PostMapping("/open")
    public void openConnection() {
        socketServer.openConnection();
    }

    @PostMapping("/close")
    public void closeConnection() {
        socketServer.closeConnection();
    }

    @GetMapping("/")
    public String getString() {
        return "abc";
    }
}
