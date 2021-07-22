package com.kms.mygram.validator;

import com.kms.mygram.domain.User;
import com.kms.mygram.dto.UserRequestDto;
import com.kms.mygram.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    private AuthService authService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequestDto userRequestDto = (UserRequestDto) target;
        if (EmailPhoneNumberValidator.isEmail(userRequestDto.getEmailOrPhoneNumber())){
            if (authService.getUserByEmail(userRequestDto.getEmailOrPhoneNumber()).isPresent())
                errors.rejectValue("emailOrPhoneNumber", "key", "이미 존재하는 Email 입니다.");
        } else if(authService.getUserByPhoneNumber(userRequestDto.getEmailOrPhoneNumber().replaceAll("[^0-9]", "")).isPresent())
            errors.rejectValue("emailOrPhoneNumber", "key", "이미 존재하는 핸드폰 번호 입니다.");
        if (authService.getUserByUserName(userRequestDto.getUsername()).isPresent())
            errors.rejectValue("username", "key", "이미 존재하는 사용자 이름 입니다.");
        checkUserName(userRequestDto.getUsername(), errors);
    }

    public static void checkUserName(String username, Errors errors) {
        String allowPattern = "^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣_.]+$";
        String exceptionPattern = "^[0-9]+$";
        Pattern p = Pattern.compile(allowPattern);
        Matcher matcher = p.matcher(username);
        if (matcher.matches()){
            p = Pattern.compile(exceptionPattern);
            matcher = p.matcher(username);
            if (matcher.matches()){
                errors.rejectValue("username", "key", "사용자 이름에는 숫자만 포함될 수 없습니다.");
            }
        } else{
            errors.rejectValue("username", "key", "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다.");
       }
    }
}
