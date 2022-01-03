package com.kms.mygram.story.repository;

import com.kms.mygram.story.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Modifying
    @Query(value = "delete from like_table where story_id=:story_id and user_id=:user_id", nativeQuery = true)
    void deleteWithStoryIdAndUserId(@Param("story_id") Long storyId, @Param("user_id") Long userId);

    @Modifying
    @Query(value = "insert into like_table(story_id, user_id, created_at) values(:story_id, :user_id, now())", nativeQuery = true)
    void createWithStoryIdAndUserId(@Param("story_id") Long storyId, @Param("user_id") Long userId);
}
