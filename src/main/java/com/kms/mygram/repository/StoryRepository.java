package com.kms.mygram.repository;

import com.kms.mygram.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
