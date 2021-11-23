package com.server.chat.repositories;

import com.server.chat.model.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	@Query(value = "select message from Message message where message.userId = ?1")
    List<Message> findMessagesByUserId(Integer userId);
    
    @Query(value = "select message from Message message where message.conversationId = ?1")
    List<Message> findMessagesByConversationId(Integer conversationId);
}
