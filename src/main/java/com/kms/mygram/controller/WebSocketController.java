package com.kms.mygram.controller;

import com.kms.mygram.dto.MessageDto;
import com.kms.mygram.service.WebSocketService;
import com.kms.mygram.utils.Utils;
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
