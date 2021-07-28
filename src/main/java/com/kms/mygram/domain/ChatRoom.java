package com.kms.mygram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomID;

    @ManyToOne
    @JoinColumn(name = "user_one_id")
    private User userOne;

    @ManyToOne
    @JoinColumn(name = "user_two_id")
    private User userTwo;

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
