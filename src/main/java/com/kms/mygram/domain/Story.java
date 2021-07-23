package com.kms.mygram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> comment;

    private String imageUrl;
    private String caption;
    private int likeNum;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void createTime() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updateTime() {
        updatedAt = LocalDateTime.now();
    }
}
