package com.kms.mygram.repository;

import com.kms.mygram.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query(value = "select * from story where user_id=:user_id order by created_at desc", nativeQuery = true)
    List<Story> findAllByUserId(@Param("user_id") Long userId);

    @Query(value = "select * from story order by created_at desc", nativeQuery = true)
    List<Story> findAll();

    @Query(value = "select * from story where user_id in :user_ids order by created_at desc", nativeQuery = true)
    List<Story> findAllByFolloweeList(@Param("user_ids") List<Long> userIdList);
}
