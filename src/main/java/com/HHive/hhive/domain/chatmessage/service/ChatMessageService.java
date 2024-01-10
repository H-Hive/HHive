package com.HHive.hhive.domain.chatmessage.service;

import com.HHive.hhive.domain.chatmessage.dto.ChatMessageRequestDTO;
import com.HHive.hhive.domain.chatmessage.dto.ChatMessageResponseDTO;
import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import com.HHive.hhive.domain.chatmessage.repository.ChatMessageRepository;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.service.HiveService;
import com.HHive.hhive.domain.relationship.hiveuser.validator.HiveUserValidator;
import com.HHive.hhive.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final HiveService hiveService;

    private final HiveUserValidator hiveUserValidator;

    public void sendChatMessages(Long hiveId, ChatMessageRequestDTO requestDTO, User user) {

        Hive hive = hiveService.findHiveById(hiveId);

        hiveUserValidator.validateHiveUser(hive, user);

        ChatMessage chatMessage = makeChatMessage(hive, requestDTO.getMessage(), user);

        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageResponseDTO> getChatMessages(Long hiveId, User user) {

        Hive hive = hiveService.findHiveById(hiveId);

        hiveUserValidator.validateHiveUser(hive, user);

        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByHiveOrderByCreatedAtDesc(hive);

        return chatMessageList.stream().map(ChatMessageResponseDTO::from).toList();
    }

    private ChatMessage makeChatMessage(Hive hive, String message, User sender) {

        return ChatMessage.builder()
                .hive(hive)
                .senderId(sender.getId())
                .senderName(sender.getUsername())
                .message(message)
                .isDeleted(false)
                .build();
    }
}
