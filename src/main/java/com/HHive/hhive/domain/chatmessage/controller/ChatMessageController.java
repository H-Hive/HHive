package com.HHive.hhive.domain.chatmessage.controller;

import com.HHive.hhive.domain.chatmessage.dto.ChatMessageRequestDTO;
import com.HHive.hhive.domain.chatmessage.dto.ChatMessageResponseDTO;
import com.HHive.hhive.domain.chatmessage.service.ChatMessageService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.global.common.CommonResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping("/hives/{hiveId}")
    public ResponseEntity<CommonResponse<Void>> sendChatMessage(
            @PathVariable Long hiveId,
            @RequestBody @Valid ChatMessageRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        chatMessageService.sendChatMessages(hiveId, requestDTO, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.of("메시지 전송 성공", null));
    }

    @GetMapping("/hives/{hiveId}")
    public ResponseEntity<CommonResponse<List<ChatMessageResponseDTO>>> getChatMessages(
            @PathVariable Long hiveId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<ChatMessageResponseDTO> responseDTOList =
                chatMessageService.getChatMessages(hiveId, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("메시지 조회 성공", responseDTOList));
    }

    @DeleteMapping("/{chat-messagesId}")
    public ResponseEntity<CommonResponse<Void>> deleteChatMessage(
            @PathVariable("chat-messagesId") Long chatMessageId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        chatMessageService.deleteChatMessage(chatMessageId, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("메시지 삭제 성공", null));
    }
}
