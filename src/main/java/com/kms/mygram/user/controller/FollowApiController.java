package com.kms.mygram.user.controller;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.user.dto.ProfileModalDto;
import com.kms.mygram.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;

    @GetMapping("/follows/{userId}/follower")
    public ResponseEntity<List<ProfileModalDto>> getFollower(@AuthenticationPrincipal Principal principal,
                                                             @PathVariable Long userId) {

        List<ProfileModalDto> profileModalDtoList =
                followService.getModalDtoByFollower(principal.getUser().getUserId(), userId);
        if (profileModalDtoList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(profileModalDtoList, HttpStatus.OK);
    }

    @GetMapping("/follows/{userId}/followee")
    public ResponseEntity<List<ProfileModalDto>> getFollowee(@AuthenticationPrincipal Principal principal,
                                                             @PathVariable Long userId) {

        List<ProfileModalDto> profileModalDtoList =
                followService.getModalDtoByFollowee(principal.getUser().getUserId(), userId);
        if (profileModalDtoList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(profileModalDtoList, HttpStatus.OK);
    }

    @PostMapping("/follows/{userId}")
    public HttpStatus Follow(@AuthenticationPrincipal Principal principal,
                             @PathVariable Long userId) {

        followService.createFollow(principal.getUser().getUserId(), userId);
        return HttpStatus.CREATED;
    }

    @DeleteMapping("/follows/{userId}")
    public HttpStatus UnFollow(@AuthenticationPrincipal Principal principal,
                               @PathVariable Long userId) {

        followService.deleteFollow(principal.getUser().getUserId(), userId);
        return HttpStatus.OK;
    }
}
