package com.kms.mygram.user.dto;

import com.kms.mygram.common.annotation.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEditDto {

    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String website;
    private String intro;
    private String gender;

    @ImageFile
    private MultipartFile image;
}