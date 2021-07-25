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
        homePageDto.setStoryList(storyService.getFolloweeStories(user.getUserId()));
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
        ProfilePageDto profilePageDto = new ProfilePageDto();
        profilePageDto.setCurrentPage("profile");
        profilePageDto.setCurrentUser(user);
        profilePageDto.setMyProfile(true);
        profilePageDto.setStoryList(storyList);
        profilePageDto.setUser(user);
        profilePageDto.setFollowerNum(followService.countByFollowee(user.getUserId()));
        profilePageDto.setFollowingNum(followService.countByFollower(user.getUserId()));
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
        profilePageDto.setCheckFollowing(followService.checkFollow(currentUser.getUserId(), targetUser.getUserId()));
        profilePageDto.setFollowerNum(followService.countByFollowee(targetUser.getUserId()));
        profilePageDto.setFollowingNum(followService.countByFollower(targetUser.getUserId()));
        profilePageDto.setStoryNum(storyList.size());
        return profilePageDto;
    }
}
