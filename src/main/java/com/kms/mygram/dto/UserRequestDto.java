package com.kms.mygram.dto;

import com.kms.mygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    private String username;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;

    public User toEntity(){
        return User.builder()
                .username(this.username)
                .name(this.name)
                .password(this.password)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .build();

    }
}
