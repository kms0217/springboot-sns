package com.kms.mygram.repository;

import com.kms.mygram.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comment where story_id=:story_id order by created_at desc", nativeQuery = true)
    List<Comment> findByStoryId(@Param("story_id") Long storyId);
}
