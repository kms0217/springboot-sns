package com.kms.mygram.dto;

import com.kms.mygram.domain.User;
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
    private boolean newRoom;
}
