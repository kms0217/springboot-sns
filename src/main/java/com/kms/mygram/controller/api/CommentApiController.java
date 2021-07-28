package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.Comment;
import com.kms.mygram.dto.CommentDto;
import com.kms.mygram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity addComment(@AuthenticationPrincipal Principal principal, @RequestBody CommentDto commentDto) {
        Comment comment = commentService.createComment(principal.getUser(), commentDto);
        return new ResponseEntity(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public HttpStatus deleteComment(@AuthenticationPrincipal Principal principal, @PathVariable Long commentId) {
        commentService.deleteComment(principal.getUser(), commentId);
        return HttpStatus.OK;
    }
}