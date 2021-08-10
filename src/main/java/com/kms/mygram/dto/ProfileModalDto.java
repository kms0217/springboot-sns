package com.kms.mygram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileModalDto {

    private Long userId;
    private String username;
    private String profileImageUrl;
    private Boolean following;
    private Boolean me;
}
