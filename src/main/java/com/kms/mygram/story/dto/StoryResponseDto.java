package com.kms.mygram.story.dto;

import com.kms.mygram.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryResponseDto{

    private Long storyId;
    private User user;
    private int likeNum;
    private int commentNum;
    private String imageUrl;
    private String caption;
}
