package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/follow/{userId}")
    public ResponseEntity Follow(@AuthenticationPrincipal Principal principal,
                                 @PathVariable Long userId
    ){
        followService.createFollow(principal.getUser().getUserId(), userId);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @DeleteMapping("/follow/{userId}")
    public ResponseEntity UnFollow(@AuthenticationPrincipal Principal principal,
                                   @PathVariable Long userId
    ){
        followService.deleteFollow(principal.getUser().getUserId(), userId);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
