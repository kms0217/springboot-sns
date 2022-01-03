package com.kms.mygram.chat.dto;

import com.kms.mygram.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomDto {

    private Long chatRoomId;
    private User otherUser;
}
