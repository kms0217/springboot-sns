package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.ChatRoom;
import com.kms.mygram.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/messages")
    public ResponseEntity getChatRooms(@AuthenticationPrincipal Principal principal) {
        List<ChatRoom> chatRooms = chatRoomService.getChatRooms(principal.getUser());
        return new ResponseEntity(chatRooms, HttpStatus.OK);
    }
}
