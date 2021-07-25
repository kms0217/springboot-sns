package com.kms.mygram.repository;

import com.kms.mygram.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Modifying
    @Query(value = "insert into follow(follower_id, followee_id, created_at) values(:follower_id, :followee_id, now())", nativeQuery = true)
    int follow(@Param("follower_id") Long follower_id, @Param("followee_id") Long followee_id);

    @Modifying
    @Query(value = "delete from follow where follower_id=:follower_id and followee_id=:followee_id", nativeQuery = true)
    int unfollow(@Param("follower_id") Long follower_id, @Param("followee_id") Long followee_id);

    @Query(value = "select * from follow where followee_id=:followee_id", nativeQuery = true)
    List<Follow> findAllByFollower(@Param("followee_id") Long userId);

    @Query(value = "select * from follow where follower_id=:follower_id", nativeQuery = true)
    List<Follow> findAllByFollowee(@Param("follower_id") Long userId);

    @Query(value = "select count(*) from follow where followee_id=:followee_id", nativeQuery = true)
    int followerCount(@Param("followee_id") Long userId);

    @Query(value = "select count(*) from follow where follower_id=:follower_id", nativeQuery = true)
    int followeeCount(@Param("follower_id") Long userId);

    @Query(value = "select count(*) from follow where follower_id=:follower_id and followee_id=:followee_id", nativeQuery = true)
    int checkFollowTarget(@Param("follower_id") Long userId, @Param("followee_id") Long targetUserId);
}
