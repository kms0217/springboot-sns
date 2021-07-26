package com.kms.mygram.dto.Page;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePageDto extends BasePageDto {

    private User user;
    private boolean myProfile;
    private boolean checkFollowing;
    private int storyNum;

    //나를 follow하는 사람
    private int followerNum;

    //내가 follow하는 사람
    private int followingNum;
    List<Story> storyList;
}