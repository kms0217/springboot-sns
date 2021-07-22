package com.kms.mygram.controller;

import com.kms.mygram.auth.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String Home(Model model, @AuthenticationPrincipal Principal principal){
        // Home Page에서 필요한 Data
        // 1. 자신이 Follow 한 User들의 게시글을 최신 기준으로 정렬해서 전송
        // 2. 추천 리스트 전송
        model.addAttribute("current", "home");
        model.addAttribute("user", principal.getUser());
        return "home";
    }

    @GetMapping("/direct")
    public String Direct(Model model, @AuthenticationPrincipal Principal principal){
        // 필요 Data
        // 1. 채팅 list
        model.addAttribute("current", "direct");
        model.addAttribute("user", principal.getUser());
        return "direct";
    }

    @GetMapping("/explore")
    public String Explore(Model model, @AuthenticationPrincipal Principal principal){
        // 필요 Data
        // 모든 User의 게시글을 최신 기준으로 정렬해서 전송
        model.addAttribute("current", "explore");
        model.addAttribute("user", principal.getUser());
        return "explore";
    }

    @GetMapping("/profile")
    public String Profile(Model model, @AuthenticationPrincipal Principal principal){
        // 필요 Data
        // 자신의 모든 게시물
        model.addAttribute("current", "profile");
        model.addAttribute("user", principal.getUser());
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String ProfileEdit(Model model, @AuthenticationPrincipal Principal principal){
        model.addAttribute("current", "profileEdit");
        model.addAttribute("user", principal.getUser());
        return "profileEdit";
    }

    @GetMapping("/profile/edit/password")
    public String ChangePassword(Model model, @AuthenticationPrincipal  Principal principal){
        model.addAttribute("current", "changePassword");
        model.addAttribute("user", principal.getUser());
        return "changePassword";
    }

    @GetMapping("/feed")
    public String Feed(Model model, @AuthenticationPrincipal Principal principal){
        model.addAttribute("current", "feed");
        model.addAttribute("user", principal.getUser());
        return "feed";
    }
}
