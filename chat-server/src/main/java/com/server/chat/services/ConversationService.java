package com.server.chat.services;

import java.util.List;

import com.server.chat.model.Conversation;

public interface ConversationService {
    Conversation create(Conversation conversation);
    Conversation findById(Integer id);
    List<Conversation> getConversationsByUserId(Integer userId);
}
