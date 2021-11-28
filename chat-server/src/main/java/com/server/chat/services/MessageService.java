package com.server.chat.services;

import java.util.List;

import com.server.chat.model.Message;

public interface MessageService {
	List<Message> getMessagesByConversationId(Integer conversationId);
}
