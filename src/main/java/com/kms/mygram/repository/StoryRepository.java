package com.kms.mygram.repository;

import com.kms.mygram.domain.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query(value = "select * from story where user_id=:user_id order by created_at desc", nativeQuery = true)
    List<Story> findAllByUserId(@Param("user_id") Long userId);

    @Query(value = "select * from story order by created_at desc", nativeQuery = true)
    List<Story> findAll();

    @Query(value = "select * from story a where a.user_id in (select b.followee_id from follow b where b.follower_id = :user_id)", nativeQuery = true)
    Page<Story> getFolloweeStories(@Param("user_id") Long userId, Pageable pageable);
}
