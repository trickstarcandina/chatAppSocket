package com.server.chat.services.impl;

import com.server.chat.model.Conversation;
import com.server.chat.repositories.ConversationRepository;
import com.server.chat.services.ConversationService;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    @Override
    public Conversation create(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation findById(Integer id) {
        return conversationRepository.findById(id).orElse(null);
    }

	@Override
	public List<Conversation> getConversationsByUserId(Integer userId) {
		return conversationRepository.findConversationsByUserId(userId);
	}
}