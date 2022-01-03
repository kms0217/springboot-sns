package com.kms.mygram.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kms.mygram.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "like_table")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "story_id")
    @JsonIgnore
    @ToString.Exclude
    private Story story;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void createTime() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updateTime() {
        updatedAt = LocalDateTime.now();
    }
}
