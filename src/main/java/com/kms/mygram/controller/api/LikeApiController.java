package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeApiController {

    private final LikeService likeService;

    @PostMapping("/likes/{storyId}")
    public HttpStatus like(@AuthenticationPrincipal Principal principal, @PathVariable Long storyId) {
        likeService.createLike(storyId, principal.getUser());
        return HttpStatus.CREATED;
    }

    @DeleteMapping("/likes/{storyId}")
    public HttpStatus unlike(@AuthenticationPrincipal Principal principal, @PathVariable Long storyId) {
        likeService.deleteLike(storyId, principal.getUser());
        return HttpStatus.OK;
    }
}
