package com.server.chat.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.chat.model.Message;
import com.server.chat.repositories.MessageRepository;
import com.server.chat.services.MessageService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {
	private final MessageRepository messageRepository;

	@Override
	public List<Message> getMessagesByUserId(Integer userId) {
		return messageRepository.findMessagesByUserId(userId);
	}

	@Override
	public List<Message> getMessagesByConversationId(Integer conversationId) {
		return messageRepository.findMessagesByConversationId(conversationId);
	}

}
