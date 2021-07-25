package com.kms.mygram.dto;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ProfilePageDto extends BasePageDto{

    private User user;
    private boolean myProfile;
    private int storyNum;
    private int followerNum;
    private int followeeNum;
    List<Story> storyList = new ArrayList<>();
}
