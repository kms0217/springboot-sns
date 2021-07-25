package com.kms.mygram.dto.Page;

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
    private boolean following;
    private boolean me;
}
