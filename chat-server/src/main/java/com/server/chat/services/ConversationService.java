package com.server.chat.services;

import com.server.chat.model.Conversation;

public interface ConversationService {
    Conversation create(Conversation conversation);
    Conversation findById(Integer id);
}
