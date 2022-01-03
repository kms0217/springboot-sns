package com.kms.mygram.chat.service;

import com.kms.mygram.chat.entity.ChatRoom;
import com.kms.mygram.chat.entity.Message;
import com.kms.mygram.user.entity.User;
import com.kms.mygram.common.exception.ApiForbiddenException;
import com.kms.mygram.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    public List<Message> getChatRoomMessages(User user, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        if (!chatRoom.getUserOne().getUserId().equals(user.getUserId()) && !chatRoom.getUserTwo().getUserId().equals(user.getUserId())) {
            throw new ApiForbiddenException("해당 채팅방에 참여중이 아닙니다.");
        }
        return messageRepository.findAllByChatRoomId(chatRoomId);
    }

    @Transactional
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}
