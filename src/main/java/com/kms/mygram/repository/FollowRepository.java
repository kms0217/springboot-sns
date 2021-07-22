package com.kms.mygram.repository;

import com.kms.mygram.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerUserIdAndFolloweeUserId(Long followerId, Long followeeId);
}
