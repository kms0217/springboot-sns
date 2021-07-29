package com.kms.mygram.service;

import com.kms.mygram.domain.Follow;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.Page.ProfileModalDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public List<Follow> findAllFollows() {
        return followRepository.findAll();
    }

    @Transactional
    public void createFollow(Long followerId, Long followeeId) {
        try {
            followRepository.follow(followerId, followeeId);
        } catch (Exception e) {
            throw new ApiException("Follow 실패");
        }
    }

    @Transactional
    public void deleteFollow(Long followerId, Long followeeId) {
        try {
            followRepository.unfollow(followerId, followeeId);
        } catch (Exception e) {
            throw new ApiException("Follow 상태가 아닙니다.");
        }
    }

    // user Id가 Follower인 갯수
    public int countByFollower(Long userId) {
        return followRepository.countByFollowerId(userId);
    }


    //user Id가 Followee인 개수
    public int countByFollowee(Long userId) {
        return followRepository.countByFolloweeId(userId);
    }

    public boolean checkFollow(Long userId, Long targetUserId) {
        return followRepository.checkFollowTarget(userId, targetUserId) > 0;
    }

    public List<ProfileModalDto> getModalDtoByFollower(Long userId, Long targetUserId) {
        List<Follow> followList = followRepository.findAllByFollower(targetUserId);
        List<ProfileModalDto> profileModalDtoList = new ArrayList<>();
        for (Follow follow : followList) {
            User followeeUser = follow.getFollowee();
            profileModalDtoList.add(ProfileModalDto.builder()
                    .following(checkFollow(userId, followeeUser.getUserId()))
                    .profileImageUrl(followeeUser.getProfileImageUrl())
                    .me(userId.equals(followeeUser.getUserId()))
                    .username(followeeUser.getUsername())
                    .userId(followeeUser.getUserId())
                    .build());
        }
        return profileModalDtoList;
    }

    public List<ProfileModalDto> getModalDtoByFollowee(Long userId, Long targetUserId) {
        List<Follow> followList = followRepository.findAllByFollowee(targetUserId);
        List<ProfileModalDto> profileModalDtoList = new ArrayList<>();
        for (Follow follow : followList) {
            User followerUser = follow.getFollower();
            profileModalDtoList.add(ProfileModalDto.builder()
                    .following(checkFollow(userId, followerUser.getUserId()))
                    .profileImageUrl(followerUser.getProfileImageUrl())
                    .me(userId.equals(followerUser.getUserId()))
                    .userId(followerUser.getUserId())
                    .username(followerUser.getUsername())
                    .build());
        }
        return profileModalDtoList;
    }
}
