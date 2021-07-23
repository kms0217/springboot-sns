package com.kms.mygram.repository;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findAllByUser(User user);
}
