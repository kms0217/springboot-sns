package com.kms.mygram.service;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.*;
import com.kms.mygram.exception.PageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
        homePageDto.setUserList(userService.getAllUsers());
        homePageDto.setStoryList(storyService.getFolloweeStories(user.getUserId()));
        return homePageDto;
    }

    public DirectPageDto directPage(User user) {
        DirectPageDto directPageDto = new DirectPageDto();
        directPageDto.setCurrentPage("direct");
        directPageDto.setCurrentUser(user);
        return directPageDto;
    }

    public ExplorerDto explorerPage(User user) {
        ExplorerDto explorerDto = new ExplorerDto();
        explorerDto.setCurrentPage("explorer");
        explorerDto.setCurrentUser(user);
        explorerDto.setStoryList(storyService.findAllStories());
        return explorerDto;
    }

    public ProfilePageDto myProfilePage(User user) {
        List<Story> storyList = storyService.findAllStoriesByUser(user);
        ProfilePageDto profilePageDto = new ProfilePageDto();
        profilePageDto.setCurrentPage("profile");
        profilePageDto.setCurrentUser(user);
        profilePageDto.setMyProfile(true);
        profilePageDto.setStoryList(storyList);
        profilePageDto.setUser(user);
        profilePageDto.setFolloweeNum(followService.getFolloweeNum(user.getUserId()));
        profilePageDto.setFollowerNum(followService.getFollowerNum(user.getUserId()));
        profilePageDto.setStoryNum(storyList.size());
        return profilePageDto;
    }

    public ProfilePageDto TargetProfilePage(User currentUser, String targetUsername) {
        User targetUser = userService.getUserByUsername(targetUsername);
        if (targetUser == null)
            throw new PageException("없는 사용자 입니다.");
        List<Story> storyList = storyService.findAllStoriesByUser(targetUser);
        ProfilePageDto profilePageDto = new ProfilePageDto();
        profilePageDto.setCurrentPage("profile");
        profilePageDto.setCurrentUser(currentUser);
        profilePageDto.setMyProfile(currentUser.getUserId() == targetUser.getUserId());
        profilePageDto.setStoryList(storyList);
        profilePageDto.setUser(targetUser);
        profilePageDto.setFolloweeNum(followService.getFolloweeNum(targetUser.getUserId()));
        profilePageDto.setFollowerNum(followService.getFollowerNum(targetUser.getUserId()));
        profilePageDto.setStoryNum(storyList.size());
        return profilePageDto;
    }
}
