package com.kms.mygram.chat.service;

import com.kms.mygram.chat.entity.Message;
import com.kms.mygram.chat.dto.MessageDto;
import com.kms.mygram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessageSendingOperations messageSendingOperations;
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @Transactional
    public void newMessage(String userName, MessageDto messageDto) {
        Message message = Message.builder()
                .content(messageDto.getContent())
                .user(userService.getUserByUsername(userName))
                .chatRoom(chatRoomService.getChatRoomById(messageDto.getChatRoomId()))
                .build();
        message = messageService.createMessage(message);
        messageSendingOperations.convertAndSend(
                "/sub/chatroom/" + message.getChatRoom().getChatRoomId(), message
        );
    }
}
