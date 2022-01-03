package com.kms.mygram.story.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kms.mygram.user.entity.User;
import lombok.*;
import org.hibernate.annotations.Formula;

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
    private Long storyId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "story")
    private List<Comment> comments;

    @OneToMany(mappedBy = "story")
    @JsonIgnore
    @ToString.Exclude
    private List<Like> likes;

    @Formula("(select count(*) from like_table l where l.story_id=story_id)")
    private int likeNum;

    @Formula("(select count(*) from comment c where c.story_id=story_id)")
    private int commentNum;

    @Transient
    private boolean likeStatus;

    private String imageUrl;
    private String caption;


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
