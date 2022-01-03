package com.kms.mygram.story.service;

import com.kms.mygram.story.entity.Comment;
import com.kms.mygram.story.entity.Story;
import com.kms.mygram.user.entity.User;
import com.kms.mygram.story.dto.CommentDto;
import com.kms.mygram.common.exception.ApiException;
import com.kms.mygram.common.exception.ApiForbiddenException;
import com.kms.mygram.story.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final StoryService storyService;

    public List<Comment> getCommentByStoryId(Long storyId) {
        return commentRepository.findByStoryId(storyId);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new ApiException("존재하지 않는 Comment 입니다.")
        );
    }

    @Transactional
    public Comment createComment(User user, CommentDto commentDto) {
        Story story = storyService.getStoryById(commentDto.getStoryId());
        Comment comment = new Comment();
        comment.setCommentMsg(commentDto.getCommentMsg());
        comment.setUser(user);
        comment.setStory(story);
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException("해당 댓글이 존재하지 않습니다."));
        if (!comment.getUser().getUserId().equals(user.getUserId()))
            throw new ApiForbiddenException("본인의 댓글만 지울 수 있습니다.");
    }
}
