package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.Message;
import com.kms.mygram.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageApiController {

    private final MessageService messageService;

    @GetMapping("/chatrooms/{chatRoomId}/messages")
    public ResponseEntity getChatRoomMessage(@AuthenticationPrincipal Principal principal, @PathVariable Long chatRoomId) {

        List<Message> messages = messageService.getChatRoomMessages(principal.getUser(), chatRoomId);
        return new ResponseEntity(messages, HttpStatus.OK);
    }
}
