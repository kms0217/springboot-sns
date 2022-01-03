package com.kms.mygram.chat.controller;

import com.kms.mygram.chat.dto.MessageDto;
import com.kms.mygram.chat.service.WebSocketService;
import com.kms.mygram.common.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;

    @MessageMapping("/chatroom")
    public void message(MessageDto messageDto, Principal principal) {
        if (Utils.isBlank(messageDto.getContent()))
            return;
        webSocketService.newMessage(principal.getName(), messageDto);
    }
}
