package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.Comment;
import com.kms.mygram.dto.CommentDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getComments(@RequestParam Long storyId) {
        List<Comment> comments = commentService.getStoryComment(storyId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@AuthenticationPrincipal Principal principal, @Valid @RequestBody CommentDto commentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ApiException("댓글을 입력해 주세요.");
        Comment comment = commentService.createComment(principal.getUser(), commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public HttpStatus deleteComment(@AuthenticationPrincipal Principal principal, @PathVariable Long commentId) {
        commentService.deleteComment(principal.getUser(), commentId);
        return HttpStatus.OK;
    }
}