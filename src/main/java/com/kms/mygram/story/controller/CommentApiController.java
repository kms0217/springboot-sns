package com.kms.mygram.story.controller;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.story.entity.Comment;
import com.kms.mygram.story.dto.CommentDto;
import com.kms.mygram.story.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getCommentsByStoryId(@RequestParam Long storyId) {
        List<Comment> comments = commentService.getCommentByStoryId(storyId);
        if (comments.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<Void> addComment(@AuthenticationPrincipal Principal principal,
                                           @Valid @RequestBody CommentDto commentDto,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) {

        Comment comment = commentService.createComment(principal.getUser(), commentDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/api/comments/{commentId}").buildAndExpand(comment.getCommentId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public HttpStatus deleteComment(@AuthenticationPrincipal Principal principal, @PathVariable Long commentId) {
        commentService.deleteComment(principal.getUser(), commentId);
        return HttpStatus.OK;
    }
}