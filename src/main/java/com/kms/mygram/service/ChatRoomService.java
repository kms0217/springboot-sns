package com.kms.mygram.service;

import com.kms.mygram.domain.ChatRoom;
import com.kms.mygram.domain.User;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> getChatRooms(User user) {
        return chatRoomRepository.findAllByUserId(user.getUserId());
    }

    public ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(()->
            new ApiException("chatRoom이 존재하지 않습니다."));
    }
}
