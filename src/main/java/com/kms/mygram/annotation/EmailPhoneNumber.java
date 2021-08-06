package com.kms.mygram.annotation;

import com.kms.mygram.validator.EmailPhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {EmailPhoneNumberValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailPhoneNumber {

    String message() default "{이메일, 휴대전화 형식에 맞지 않습니다.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
