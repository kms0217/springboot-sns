package com.kms.mygram.user.entity;

import com.kms.mygram.user.dto.ProfileModalDto;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"follower_id", "followee_id"}
        )
})
@SqlResultSetMapping(
        name = "ProfileModalDtoMapping",
        classes = @ConstructorResult(
                targetClass = ProfileModalDto.class,
                columns = {
                        @ColumnResult(name = "user_id", type = Long.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "profile_image_url", type = String.class),
                        @ColumnResult(name = "following", type = Boolean.class),
                        @ColumnResult(name = "me", type = Boolean.class)
                }
        )
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followee_id")
    private User followee;

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
