package com.kms.mygram.validator;

import com.kms.mygram.domain.User;
import com.kms.mygram.dto.UserRequestDto;
import com.kms.mygram.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
    }
}
