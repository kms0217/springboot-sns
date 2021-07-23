package com.kms.mygram.controller;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.BasePageDto;
import com.kms.mygram.dto.ExplorerDto;
import com.kms.mygram.dto.HomePageDto;
import com.kms.mygram.dto.ProfilePageDto;
import com.kms.mygram.service.StoryService;
import com.kms.mygram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;
    private final StoryService storyService;

    @GetMapping("/")
    public String Home(Model model, @AuthenticationPrincipal Principal principal){
        //TODO 팔로우 추천 리스트 동작
        //TODO User의 follower Story불러오기
        List<User> userList = userService.getAllUsers();
        User user = principal.getUser();
        userList.remove(user);
        BasePageDto basePageDto = new BasePageDto("home", user);
        HomePageDto homePageDto = new HomePageDto(null, userService.getAllUsers());

        model.addAttribute("baseInfo", basePageDto);
        model.addAttribute("pageInfo", homePageDto);
        return "home";
    }

    @GetMapping("/direct")
    public String Direct(Model model, @AuthenticationPrincipal Principal principal){
        BasePageDto basePageDto= new BasePageDto("direct", principal.getUser());
        model.addAttribute("baseInfo", basePageDto);
        return "direct";
    }

    @GetMapping("/explore")
    public String Explore(Model model, @AuthenticationPrincipal Principal principal){
        // 모든 User의 게시글을 최신 기준으로 정렬해서 전송
        BasePageDto basePageDto= new BasePageDto("explore", principal.getUser());
        ExplorerDto explorerDto = new ExplorerDto(storyService.findAllStories());
        model.addAttribute("baseInfo", basePageDto);
        model.addAttribute("pageInfo", explorerDto);
        return "explore";
    }

    @GetMapping("/profile")
    public String Profile(Model model, @AuthenticationPrincipal Principal principal){
        User user = principal.getUser();
        ProfilePageDto profilePageDto = new ProfilePageDto(user, true, 0,0,0,
                storyService.findAllStoriesByUser(principal.getUser()));
        BasePageDto basePageDto = new BasePageDto("profile", user);
        model.addAttribute("baseInfo", basePageDto);
        model.addAttribute("pageInfo", profilePageDto);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String ProfileEdit(Model model, @AuthenticationPrincipal Principal principal){
        BasePageDto basePageDto= new BasePageDto("profileEdit", principal.getUser());
        model.addAttribute("baseInfo", basePageDto);
        return "profileEdit";
    }

    @GetMapping("/profile/edit/password")
    public String ChangePassword(Model model, @AuthenticationPrincipal  Principal principal){
        BasePageDto basePageDto= new BasePageDto("editPassword", principal.getUser());
        model.addAttribute("baseInfo", basePageDto);
        return "changePassword";
    }

    @GetMapping("/feed")
    public String Feed(Model model, @AuthenticationPrincipal Principal principal){
        BasePageDto basePageDto= new BasePageDto("feed", principal.getUser());
        model.addAttribute("baseInfo", basePageDto);
        return "feed";
    }

    @GetMapping("/upload")
    public String Upload(Model model, @AuthenticationPrincipal Principal principal){
        BasePageDto basePageDto= new BasePageDto("upload", principal.getUser());
        model.addAttribute("baseInfo", basePageDto);
        return "uploadPost";
    }
}
