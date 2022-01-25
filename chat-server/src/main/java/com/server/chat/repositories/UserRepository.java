package com.server.chat.repositories;


import com.server.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    @Query(value = "select * from users inner join user_conversation on user_id = users.id and conversation_id = ?1", nativeQuery = true)
    List<User> findUsersByConversationId(Integer conversationId);

    @Modifying
    @Query(value = "update users u set u.address = COALESCE(?, u.address), " +
            "u.nick_name = COALESCE(?, u.nick_name), u.password = COALESCE(?, u.password) " +
            "where u.username = ?",
            nativeQuery = true)
    void updateUser(String address, String nickName, String password, String username);

    @Query(value = "select count(*) from users u where u.username = ?", nativeQuery = true)
    Integer checkUserNameExists(String username);
}
