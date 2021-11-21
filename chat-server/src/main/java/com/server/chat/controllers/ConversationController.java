package com.server.chat.controllers;

import com.server.chat.model.Conversation;
import com.server.chat.services.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversation")
@AllArgsConstructor
public class ConversationController {

    private ConversationService conversationService;

    @GetMapping
    public ResponseEntity<Conversation> findById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(conversationService.findById(id));
    }
}
