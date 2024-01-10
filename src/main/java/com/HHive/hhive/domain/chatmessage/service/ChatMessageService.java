package com.HHive.hhive.domain.chatmessage.service;

import com.HHive.hhive.domain.chatmessage.dto.SendChatMessageDTO;
import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import com.HHive.hhive.domain.chatmessage.repository.ChatMessageRepository;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.hive.service.HiveService;
import com.HHive.hhive.domain.relationship.hiveuser.repository.HiveUserRepository;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.exception.hive.NotFoundHiveException;
import com.HHive.hhive.global.exception.user.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final HiveService hiveService;

    public void sendChatMessages(Long hiveId, SendChatMessageDTO requestDTO, User user) {

        Hive hive = hiveService.findHiveById(hiveId);

        ChatMessage chatMessage = makeChatMessage(hive, requestDTO.getMessage(), user.getId());

        chatMessageRepository.save(chatMessage);
    }

    private ChatMessage makeChatMessage(Hive hive, String message, Long userId) {

        return ChatMessage.builder()
                .hive(hive)
                .senderId(userId)
                .message(message)
                .isDeleted(false)
                .build();
    }
}
