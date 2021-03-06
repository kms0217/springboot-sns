package com.kms.mygram.story.repository;

import com.kms.mygram.story.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comment where story_id=:story_id", nativeQuery = true)
    List<Comment> findByStoryId(@Param("story_id") Long storyId);
}
