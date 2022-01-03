package com.kms.mygram.chat.controller;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.chat.entity.ChatRoom;
import com.kms.mygram.chat.dto.ChatRoomDto;
import com.kms.mygram.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chatrooms")
    public ResponseEntity<List<ChatRoomDto>> getAllChatRoomDtoByLoginUser(@AuthenticationPrincipal Principal principal) {
        List<ChatRoomDto> chatRoomDtoList = chatRoomService.getChatRoomsDto(principal.getUser());
        if (chatRoomDtoList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(chatRoomDtoList, HttpStatus.OK);
    }

    @GetMapping("/chatrooms/{chatroomId}")
    public ResponseEntity<ChatRoomDto> getChatRoomDto(@AuthenticationPrincipal Principal principal,
                                                      @PathVariable Long chatroomId) {

        ChatRoomDto chatRoomDto = chatRoomService.getChatRoomDto(principal.getUser(), chatroomId);
        return new ResponseEntity<>(chatRoomDto, HttpStatus.OK);
    }

    @PostMapping("/chatrooms")
    public ResponseEntity<Void> createChatRoom(@AuthenticationPrincipal Principal principal,
                                                      @RequestParam Long targetUserId,
                                                      UriComponentsBuilder uriComponentsBuilder) {

        ChatRoom chatRoom = chatRoomService.createChatRoom(principal.getUser().getUserId(), targetUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/api/chatrooms/{chatroomId}").buildAndExpand(chatRoom.getChatRoomId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
