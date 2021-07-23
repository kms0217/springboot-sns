package com.kms.mygram.dto;

import com.kms.mygram.Annotation.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryRequestDto {

    @ImageFile
    private MultipartFile image;

    private String caption;
}
