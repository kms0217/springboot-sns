package com.kms.mygram.service;

import com.kms.mygram.domain.ChatRoom;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.ChatRoomDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    public ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
                new ApiException("chatroom이 존재하지 않습니다."));
    }

    public ChatRoomDto createChatRoom(Long userId, Long targetUserId) {
        User userTwo = userService.getUserById(targetUserId);
        ChatRoom chatRoom = chatRoomRepository.findByUserOneIdAndUserTwoId(userId, targetUserId).orElse(null);
        if (chatRoom != null) {
            return ChatRoomDto.builder()
                    .otherUser(userTwo)
                    .chatRoomId(chatRoom.getChatRoomId())
                    .newRoom(false).build();
        }
        User userOne = userService.getUserById(userId);
        chatRoom = ChatRoom.builder()
                .userOne(userOne)
                .userTwo(userTwo)
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);
        return ChatRoomDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .otherUser(userTwo)
                .newRoom(true)
                .build();
    }

    public List<ChatRoomDto> getChatRoomsDto(User user) {
        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
        chatRoomRepository.findAllByUserId(user.getUserId()).forEach(chatRoom -> {
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setChatRoomId(chatRoom.getChatRoomId());
            chatRoomDto.setOtherUser(!chatRoom.getUserOne().getUserId().equals(user.getUserId()) ? chatRoom.getUserOne() : chatRoom.getUserTwo());
            chatRoomDto.setNewRoom(false);
            chatRoomDtoList.add(chatRoomDto);
        });
        return chatRoomDtoList;
    }
}
