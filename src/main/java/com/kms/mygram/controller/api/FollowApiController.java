package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.Follow;
import com.kms.mygram.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;

    @GetMapping("/follows")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Follow>> allFollows() {
        List<Follow> followList = followService.findAllFollows();
        if(followList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(followList, HttpStatus.OK);
    }

    @GetMapping("/follows/{userId}/follower")
    public ResponseEntity<List<Follow>> getFollower(@PathVariable Long userId) {
        List<Follow> followList = followService.findAllByFollower(userId);
        if(followList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(followList, HttpStatus.OK);
    }

    @GetMapping("/follows/{userId}/followee")
    public ResponseEntity<List<Follow>> getFollowee(@PathVariable Long userId) {
        List<Follow> followList = followService.findAllByFollowee(userId);
        if(followList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(followList, HttpStatus.OK);
    }

    @PostMapping("/follows/{userId}")
    public ResponseEntity Follow(@AuthenticationPrincipal Principal principal,
                                 @PathVariable Long userId
    ){
        followService.createFollow(principal.getUser().getUserId(), userId);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @DeleteMapping("/follows/{userId}")
    public ResponseEntity UnFollow(@AuthenticationPrincipal Principal principal,
                                   @PathVariable Long userId
    ){
        followService.deleteFollow(principal.getUser().getUserId(), userId);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
