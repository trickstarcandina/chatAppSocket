package com.server.chat.repositories;

import com.server.chat.model.MessagePending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessagePendingRepository extends JpaRepository<MessagePending, Integer> {
    @Query(value = "select message from MessagePending message where message.user.id = ?1")
    List<MessagePending> findByUser(Integer userId);
}
