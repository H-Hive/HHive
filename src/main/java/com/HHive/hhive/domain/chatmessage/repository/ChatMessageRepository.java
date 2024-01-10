package com.HHive.hhive.domain.chatmessage.repository;

import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import com.HHive.hhive.domain.hive.entity.Hive;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.isDeleted = false AND cm.hive = :hive")
    List<ChatMessage> findAllChatMessageByHive(Hive hive);
}
