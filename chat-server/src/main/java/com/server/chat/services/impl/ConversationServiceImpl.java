package com.server.chat.services.impl;

import com.server.chat.model.Conversation;
import com.server.chat.repositories.ConversationRepository;
import com.server.chat.services.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    @Override
    public Conversation create(Conversation conversation) {
        return conversationRepository.save(conversation);
    }
}
