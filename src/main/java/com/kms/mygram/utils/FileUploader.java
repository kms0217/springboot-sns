package com.kms.mygram.utils;

import com.kms.mygram.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUploader {

    @Value("${upload.path}")
    private String rootPath;

    public String upload(MultipartFile file, String dir){
        if (file == null)
            return null;
        UUID uuid = UUID.randomUUID();
        String uniqueFileName = uuid + "_" + file.getOriginalFilename();
        Path path = Paths.get(rootPath + dir + uniqueFileName);
        try{
            Files.write(path, file.getBytes());
        }catch (Exception e){
            throw new ApiException("파일 업로드 실패");
        }
        return uniqueFileName;
    }
}
