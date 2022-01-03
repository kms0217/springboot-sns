package com.kms.mygram.story.repository;

import com.kms.mygram.story.entity.Story;
import com.kms.mygram.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {

    @EntityGraph(attributePaths = {"user", "comments", "comments.user"})
    Optional<Story> findById(Long storyId);

    List<Story> findAllByUserOrderByCreatedAtDesc(User user);

    Page<Story> findAll(Pageable pageable);

    Page<Story> findByUser(User user, Pageable pageable);

    @Query(value = "select count (a) from Story a where a.user in (select b.followee from Follow b where b.follower=:user)")
    int countFolloweeStories(@Param("user")User user);
}
