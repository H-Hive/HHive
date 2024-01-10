package com.HHive.hhive.domain.chatmessage.repository;

import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import com.HHive.hhive.domain.hive.entity.Hive;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByHiveOrderByCreatedAtDesc(Hive hive);
}
