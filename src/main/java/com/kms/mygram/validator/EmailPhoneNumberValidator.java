package com.kms.mygram.validator;

import com.kms.mygram.Annotation.EmailPhoneNumber;
import com.kms.mygram.utils.Utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailPhoneNumberValidator implements ConstraintValidator<EmailPhoneNumber, String> {

    @Override
    public void initialize(EmailPhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isPhoneNumber(value) || isEmail(value);
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return Utils.checkMatch("^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", phoneNumber);
    }

    public static boolean isEmail(String email) {
        return Utils.checkMatch("^[a-z0-9_+-]+@([a-z0-9-]+\\.)+[a-z0-9]{2,4}$", email);
    }
}


