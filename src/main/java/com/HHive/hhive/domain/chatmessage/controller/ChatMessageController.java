package com.HHive.hhive.domain.chatmessage.controller;

import com.HHive.hhive.domain.chatmessage.dto.SendChatMessageDTO;
import com.HHive.hhive.domain.chatmessage.service.ChatMessageService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestBody @Valid SendChatMessageDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        chatMessageService.sendChatMessages(hiveId, requestDTO, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of(200, "메시지 전송 성공", null));
    }
}
