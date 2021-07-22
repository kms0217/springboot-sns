package com.kms.mygram.service;

import com.kms.mygram.domain.Follow;
import com.kms.mygram.domain.User;
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
    public Follow createFollow(Long followerId, Long followeeId){
        User followerUser = userService.getUser(followerId);
        User followeeUser = userService.getUser(followeeId);
        Follow follow = Follow.builder()
                .follower(followerUser)
                .followee(followeeUser)
                .build();
        return followRepository.save(follow);
    }

    @Transactional
    public void deleteFollow(Long followerId, Long followeeId){
        Follow follow = followRepository.findByFollowerUserIdAndFolloweeUserId(followerId, followeeId).orElseThrow();
        followRepository.delete(follow);
    }
}
