package com.server.chat.repositories;


import com.server.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    @Query(value = "select * from users inner join user_conversation on user_id = users.id and conversation_id = ?1", nativeQuery = true)
    List<User> findUsersByConversationId(Integer conversationId);
}
