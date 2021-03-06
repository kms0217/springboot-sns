package com.kms.mygram.page.service;

import com.kms.mygram.story.entity.Story;
import com.kms.mygram.user.entity.User;
import com.kms.mygram.page.dto.DirectPageDto;
import com.kms.mygram.page.dto.ExplorerPageDto;
import com.kms.mygram.page.dto.HomePageDto;
import com.kms.mygram.page.dto.ProfilePageDto;
import com.kms.mygram.common.exception.ApiException;
import com.kms.mygram.common.exception.PageException;
import com.kms.mygram.user.service.FollowService;
import com.kms.mygram.story.service.StoryService;
import com.kms.mygram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {

    private final FollowService followService;
    private final UserService userService;
    private final StoryService storyService;

    public HomePageDto homePage(User user) {
        //TODO 현재는 모든 user -> 이후 추천 리스트로 변경
        HomePageDto homePageDto = new HomePageDto();
        homePageDto.setCurrentUser(user);
        homePageDto.setCurrentPage("home");
        homePageDto.setUserList(userService.getRecommendUsers(user.getUserId()));
        return homePageDto;
    }

    public DirectPageDto directPage(User user) {
        DirectPageDto directPageDto = new DirectPageDto();
        directPageDto.setCurrentPage("direct");
        directPageDto.setCurrentUser(user);
        return directPageDto;
    }

    public ExplorerPageDto explorerPage(User user) {
        ExplorerPageDto explorerPageDto = new ExplorerPageDto();
        explorerPageDto.setCurrentPage("explore");
        explorerPageDto.setCurrentUser(user);
        return explorerPageDto;
    }

    public ProfilePageDto myProfilePage(User user) {
        List<Story> storyList = storyService.getAllStoriesByUser(user);
        ProfilePageDto profilePageDto = ProfilePageDto.builder()
                .myProfile(true)
                .storyList(storyList)
                .user(user)
                .followerNum(followService.countByFollowee(user.getUserId()))
                .followingNum(followService.countByFollower(user.getUserId()))
                .storyNum(storyList.size())
                .build();
        profilePageDto.setCurrentPage("profile");
        profilePageDto.setCurrentUser(user);
        return profilePageDto;
    }

    public ProfilePageDto TargetProfilePage(User currentUser, String targetUsername) {
        User targetUser;
        try{
            targetUser = userService.getUserByUsername(targetUsername);
        } catch (ApiException e) {
            throw new PageException("없는 사용자 입니다.");
        }
        List<Story> storyList = storyService.getAllStoriesByUser(targetUser);
        ProfilePageDto profilePageDto = ProfilePageDto.builder()
                .myProfile(currentUser.getUserId().equals(targetUser.getUserId()))
                .storyList(storyList)
                .user(targetUser)
                .checkFollowing(followService.checkFollow(currentUser.getUserId(), targetUser.getUserId()))
                .followerNum(followService.countByFollowee(targetUser.getUserId()))
                .followingNum(followService.countByFollower(targetUser.getUserId()))
                .storyNum(storyList.size())
                .build();
        profilePageDto.setCurrentPage("profile");
        profilePageDto.setCurrentUser(currentUser);
        return profilePageDto;
    }
}
