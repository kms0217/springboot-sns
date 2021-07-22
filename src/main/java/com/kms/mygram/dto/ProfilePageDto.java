package com.kms.mygram.dto;

import com.kms.mygram.domain.Post;
import com.kms.mygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePageDto {

    private User user;
    private boolean myProfile;
    private int postNum;
    private int followerNum;
    private int followeeNum;
    List<Post> postList = new ArrayList<>();
}
