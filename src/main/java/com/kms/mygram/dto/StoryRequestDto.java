package com.kms.mygram.dto;

import com.kms.mygram.annotation.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryRequestDto {

    @NotNull
    @ImageFile
    private MultipartFile image;

    private String caption;
}
