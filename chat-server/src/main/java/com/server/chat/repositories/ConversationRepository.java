package com.server.chat.repositories;

import com.server.chat.model.Conversation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
	@Query(value = "select * from conversation inner join user_conversation on conversation_id = conversation.id and user_id = ?1", nativeQuery = true)
	List<Conversation> findConversationsByUserId(Integer userId);
}
