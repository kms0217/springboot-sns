package com.kms.mygram.page.dto;

import com.kms.mygram.story.entity.Story;
import com.kms.mygram.user.entity.User;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ProfilePageDto extends BasePageDto {

    private User user;
    private boolean myProfile;
    private boolean checkFollowing;
    private int storyNum;
    private int followerNum;
    private int followingNum;
    List<Story> storyList;
}
