package com.HHive.hhive.domain.chatmessage.controller;

import com.HHive.hhive.domain.chatmessage.dto.ChatMessageRequestDTO;
import com.HHive.hhive.domain.chatmessage.dto.ChatMessageResponseDTO;
import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import com.HHive.hhive.domain.chatmessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessageSendingOperations sendingOperations;

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void socketHandler(ChatMessageRequestDTO requestDTO) {

        ChatMessage chatMessage = chatMessageService.receiveAndSaveMessage(requestDTO);

        sendingOperations.convertAndSend("/topic/chat/" + requestDTO.getHiveId(),
                ChatMessageResponseDTO.from(chatMessage));
    }

}
