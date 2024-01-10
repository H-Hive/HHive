package com.HHive.hhive.domain.chatmessage.repository;

import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
