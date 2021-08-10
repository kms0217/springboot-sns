package com.kms.mygram.service;

import com.kms.mygram.dto.ProfileModalDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final EntityManager em;

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
        return em.createNativeQuery("select u.user_id, u.username, u.profile_image_url,\n" +
                "if((select 1 from follow where follower_id=:userId and followee_id=u.user_id), 1, 0) as following,\n" +
                "if(u.user_id=:userId, 1, 0) as me\n" +
                "from follow f inner join user u on f.followee_id=u.user_id where f.follower_id=:targetUserId", "ProfileModalDtoMapping")
                .setParameter("userId", userId)
                .setParameter("targetUserId", targetUserId)
                .getResultList();
    }

    public List<ProfileModalDto> getModalDtoByFollowee(Long userId, Long targetUserId) {
        return em.createNativeQuery("select u.user_id, u.username, u.profile_image_url,\n" +
                "if((select 1 from follow where follower_id=:userId and followee_id=u.user_id), 1, 0) as following,\n" +
                "if(u.user_id=:userId, 1, 0) as me\n" +
                "from follow f inner join user u on f.follower_id=u.user_id where f.followee_id=:targetUserId", "ProfileModalDtoMapping")
                .setParameter("userId", userId)
                .setParameter("targetUserId", targetUserId)
                .getResultList();
    }
}
