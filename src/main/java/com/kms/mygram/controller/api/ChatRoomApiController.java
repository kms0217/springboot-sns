package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.dto.ChatRoomDto;
import com.kms.mygram.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chatrooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRooms(@AuthenticationPrincipal Principal principal) {
        List<ChatRoomDto> chatRoomDtoList = chatRoomService.getChatRoomsDto(principal.getUser());
        return new ResponseEntity<>(chatRoomDtoList, HttpStatus.OK);
    }

    @PostMapping("/chatrooms")
    public ResponseEntity<ChatRoomDto> createChatRoom(@AuthenticationPrincipal Principal principal, @RequestParam Long targetUserId) {
        ChatRoomDto chatRoomDto = chatRoomService.createChatRoom(principal.getUser().getUserId(), targetUserId);
        return new ResponseEntity<>(chatRoomDto, HttpStatus.OK);
    }
}
