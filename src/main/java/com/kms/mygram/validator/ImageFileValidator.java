package com.kms.mygram.validator;

import com.kms.mygram.Annotation.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {

    @Override
    public void initialize(ImageFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null)
            return true;
        try {
            ImageIO.read(file.getInputStream()).toString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

