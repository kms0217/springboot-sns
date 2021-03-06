package com.kms.mygram.page.controller;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.page.dto.BasePageDto;
import com.kms.mygram.page.dto.DirectPageDto;
import com.kms.mygram.page.dto.ExplorerPageDto;
import com.kms.mygram.page.dto.HomePageDto;
import com.kms.mygram.page.dto.ProfilePageDto;
import com.kms.mygram.page.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping("/")
    public String Home(Model model, @AuthenticationPrincipal Principal principal) {
        HomePageDto dto = pageService.homePage(principal.getUser());
        model.addAttribute("pageInfo", dto);
        return "home";
    }

    @GetMapping("/direct")
    public String Direct(Model model, @AuthenticationPrincipal Principal principal) {
        DirectPageDto dto = pageService.directPage(principal.getUser());
        model.addAttribute("pageInfo", dto);
        return "direct";
    }

    @GetMapping("/explore")
    public String Explore(Model model, @AuthenticationPrincipal Principal principal) {
        ExplorerPageDto dto = pageService.explorerPage(principal.getUser());
        model.addAttribute("pageInfo", dto);
        return "explore";
    }

    @GetMapping("/profile")
    public String Profile(Model model, @AuthenticationPrincipal Principal principal) {
        ProfilePageDto dto = pageService.myProfilePage(principal.getUser());
        model.addAttribute("pageInfo", dto);
        return "profile";
    }

    @GetMapping("/profile/{targetUsername}")
    public String ShowTargetProfile(Model model,
                                    @AuthenticationPrincipal Principal principal,
                                    @PathVariable String targetUsername
    ) {
        ProfilePageDto dto = pageService.TargetProfilePage(principal.getUser(), targetUsername);
        model.addAttribute("pageInfo", dto);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String ProfileEdit(Model model, @AuthenticationPrincipal Principal principal) {
        BasePageDto dto = new BasePageDto("profileEdit", principal.getUser());
        model.addAttribute("pageInfo", dto);
        return "profileEdit";
    }

    @GetMapping("/profile/edit/password")
    public String ChangePassword(Model model, @AuthenticationPrincipal Principal principal) {
        BasePageDto dto = new BasePageDto("editPassword", principal.getUser());
        model.addAttribute("pageInfo", dto);
        return "changePassword";
    }

    @GetMapping("/feed")
    public String Feed(Model model, @AuthenticationPrincipal Principal principal) {
        BasePageDto dto = new BasePageDto("feed", principal.getUser());
        model.addAttribute("pageInfo", dto);
        return "feed";
    }

    @GetMapping("/upload")
    public String Upload(Model model, @AuthenticationPrincipal Principal principal) {
        BasePageDto dto = new BasePageDto("upload", principal.getUser());
        model.addAttribute("pageInfo", dto);
        return "uploadPost";
    }
}
