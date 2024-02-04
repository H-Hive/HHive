package com.HHive.hhive.domain.chatmessage.service;

import com.HHive.hhive.domain.chatmessage.dto.ChatMessageRequestDTO;
import com.HHive.hhive.domain.chatmessage.dto.ChatMessageResponseDTO;
import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import com.HHive.hhive.domain.chatmessage.repository.ChatMessageRepository;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.service.HiveService;
import com.HHive.hhive.domain.relationship.hiveuser.validator.HiveUserValidator;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.UserRepository;
import com.HHive.hhive.global.exception.chatmessage.NotFoundChatMessageException;
import com.HHive.hhive.global.exception.chatmessage.NotSenderOfChatMessageException;
import com.HHive.hhive.global.exception.user.NotFoundUserException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final UserRepository userRepository;

    private final HiveService hiveService;

    private final HiveUserValidator hiveUserValidator;

    public ChatMessage receiveAndSaveMessage(ChatMessageRequestDTO requestDTO) {

        User user = userRepository.findByUsername(requestDTO.getUsername()).orElseThrow(
                NotFoundUserException::new);

        Hive hive = hiveService.findHiveById(requestDTO.getHiveId());

        ChatMessage chatMessage = makeChatMessage(hive, user, requestDTO.getMessage());

        chatMessageRepository.save(chatMessage);

        return chatMessage;
    }

    public List<ChatMessageResponseDTO> getChatMessages(Long hiveId, User user) {

        Hive hive = hiveService.findHiveById(hiveId);

        hiveUserValidator.validateHiveUser(hive, user);

        List<ChatMessage> chatMessageList = chatMessageRepository.findAllChatMessageByHive(hive);

        return chatMessageList.stream().map(ChatMessageResponseDTO::from).toList();
    }

    @Transactional
    public void deleteChatMessage(Long chatMessageId, User user) {

        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId)
                .orElseThrow(NotFoundChatMessageException::new);

        validateLoginUserEqualsMessageSender(chatMessage, user);

        chatMessage.updateDeletedAt();
    }

    private void validateLoginUserEqualsMessageSender(ChatMessage chatMessage, User user) {

        if (!chatMessage.getSenderId().equals(user.getId())) {
            throw new NotSenderOfChatMessageException();
        }
    }

    private ChatMessage makeChatMessage(Hive hive, User sender, String message) {

        return ChatMessage.builder()
                .hive(hive)
                .senderId(sender.getId())
                .senderName(sender.getUsername())
                .message(message)
                .isDeleted(false)
                .build();
    }
}
