package com.kms.mygram.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(Authority.class)
public class Authority implements GrantedAuthority {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    private String authority;
}
