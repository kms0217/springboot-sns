package com.kms.mygram.validator;

import com.kms.mygram.Annotation.EmailPhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPhoneNumberValidator implements ConstraintValidator<EmailPhoneNumber, String> {

    @Override
    public void initialize(EmailPhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isPhoneNumber(value) || isEmail(value);
    }

    public boolean isPhoneNumber(String phoneNumber){
        String phonePattern = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";
        Pattern p = Pattern.compile(phonePattern);
        Matcher matcher = p.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isEmail(String email){
        String emailPattern = "^[a-z0-9_+-]+@([a-z0-9-]+\\.)+[a-z0-9]{2,4}$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }
}

