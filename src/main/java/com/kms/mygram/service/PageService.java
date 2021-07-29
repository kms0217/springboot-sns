package com.kms.mygram.service;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.Page.DirectPageDto;
import com.kms.mygram.dto.Page.ExplorerPageDto;
import com.kms.mygram.dto.Page.HomePageDto;
import com.kms.mygram.dto.Page.ProfilePageDto;
import com.kms.mygram.exception.PageException;
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
        explorerPageDto.setStoryList(storyService.findAllStories());
        return explorerPageDto;
    }

    public ProfilePageDto myProfilePage(User user) {
        List<Story> storyList = storyService.findAllStoriesByUser(user);
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
        User targetUser = userService.getUserByUsername(targetUsername);
        if (targetUser == null)
            throw new PageException("없는 사용자 입니다.");
        List<Story> storyList = storyService.findAllStoriesByUser(targetUser);
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
