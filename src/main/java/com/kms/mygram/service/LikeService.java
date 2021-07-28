package com.kms.mygram.service;

import com.kms.mygram.domain.User;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public void createLike(Long storyId, User user) {
        try{
            likeRepository.createWithStoryIdAndUserId(storyId, user.getUserId());
        } catch (Exception e) {
            throw new ApiException("좋아요 실패");
        }
    }

    @Transactional
    public void deleteLike(Long storyId, User user) {
        likeRepository.deleteWithStoryIdAndUserId(storyId, user.getUserId());
    }
}
