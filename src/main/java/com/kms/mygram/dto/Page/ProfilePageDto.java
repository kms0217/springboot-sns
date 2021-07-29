package com.kms.mygram.dto.Page;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
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
