package com.kms.mygram.service;

import com.kms.mygram.domain.Follow;
import com.kms.mygram.domain.User;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    @Transactional
    public void createFollow(Long followerId, Long followeeId){
        try{
            followRepository.follow(followerId, followeeId);
        } catch (Exception e){
            throw new ApiException("Follow 실패");
        }
    }

    @Transactional
    public void deleteFollow(Long followerId, Long followeeId){
        try{
            followRepository.unfollow(followerId, followeeId);
        } catch (Exception e){
            throw new ApiException("Follow 상태가 아닙니다.");
        }
    }
}
