package com.kms.mygram.service;

import com.kms.mygram.domain.Comment;
import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.CommentDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.exception.ApiForbiddenException;
import com.kms.mygram.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final StoryService storyService;

    public Comment createComment(User user, CommentDto commentDto) {
        Story story = storyService.findById(commentDto.getStoryId());
        // TODO 여기서 처리할 필요 없이 storyService에서 Throw해주면 될듯.
        if (story == null)
            throw new ApiException("해당 페이지가 존재하지 않습니다.");
        Comment comment = new Comment();
        comment.setCommentMsg(commentDto.getCommentMsg());
        comment.setUser(user);
        comment.setStory(story);
        return commentRepository.save(comment);
    }

    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException("해당 댓글이 존재하지 않습니다."));
        if (comment.getUser().getUserId() != user.getUserId())
            throw new ApiForbiddenException("본인의 댓글만 지울 수 있습니다.");
    }

    public List<Comment> getStoryComment(Long storyId) {
        return commentRepository.findByStoryId(storyId);
    }
}
