package com.drcs.chat;

import com.drcs.helprequest.HelpRequest;
import com.drcs.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    List<ChatMessage> findByHelpRequestOrderBySentAtAsc(HelpRequest helpRequest);

    @Query("SELECT c FROM ChatMessage c WHERE (c.sender = :user1 AND c.receiver = :user2) OR (c.sender = :user2 AND c.receiver = :user1) ORDER BY c.sentAt ASC")
    List<ChatMessage> findConversationBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);
}