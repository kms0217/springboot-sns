package com.kms.mygram.service;

import com.kms.mygram.domain.ChatRoom;
import com.kms.mygram.domain.Message;
import com.kms.mygram.domain.User;
import com.kms.mygram.exception.ApiForbiddenException;
import com.kms.mygram.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    public List<Message> getChatRoomMessages(User user, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);
        if (!chatRoom.getUserOne().getUserId().equals(user.getUserId())  && !chatRoom.getUserTwo().getUserId().equals(user.getUserId())) {
            throw new ApiForbiddenException("해당 채팅방에 참여중이 아닙니다.");
        }
        return messageRepository.findAllByChatRoomId(chatRoomId);
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}
