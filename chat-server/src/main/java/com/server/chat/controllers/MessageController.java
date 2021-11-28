package com.server.chat.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.chat.model.Message;
import com.server.chat.services.MessageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {
	private MessageService messageService;
	
	@GetMapping("/conversation")
    private ResponseEntity<List<Message>> getMessagesByConvarsation(@RequestParam("conversationId") Integer conversationId){
    	return ResponseEntity.ok(messageService.getMessagesByConversationId(conversationId));
    }
}
