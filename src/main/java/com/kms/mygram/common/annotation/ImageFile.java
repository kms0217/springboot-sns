package com.kms.mygram.common.annotation;

import com.kms.mygram.common.validator.ImageFileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ImageFileValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageFile {

    String message() default "{잘못된 파일 형식입니다.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}