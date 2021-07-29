package com.kms.mygram.validator;

import com.kms.mygram.dto.UserRequestDto;
import com.kms.mygram.service.AuthService;
import com.kms.mygram.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final AuthService authService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserRequestDto target = (UserRequestDto) object;

        if (EmailPhoneNumberValidator.isEmail(target.getEmailOrPhoneNumber())) {
            if (authService.getUserByEmail(target.getEmailOrPhoneNumber()).isPresent())
                errors.rejectValue("emailOrPhoneNumber", "key", "이미 존재하는 Email 입니다.");
        } else if (authService.getUserByPhoneNumber(target.getEmailOrPhoneNumber().replaceAll("[^0-9]", "")).isPresent())
            errors.rejectValue("emailOrPhoneNumber", "key", "이미 존재하는 핸드폰 번호 입니다.");
        if (authService.getUserByUserName(target.getUsername()).isPresent())
            errors.rejectValue("username", "key", "이미 존재하는 사용자 이름 입니다.");
        checkUserName(target.getUsername(), errors);
    }

    public static void checkUserName(String username, Errors errors) {
        if (!Utils.checkMatch("^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣_.]+$", username)) {
            errors.rejectValue("username", "key", "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다.");
            return;
        }
        if (Utils.checkMatch("^[0-9]+$", username))
            errors.rejectValue("username", "key", "사용자 이름에는 숫자만 포함될 수 없습니다.");
    }
}
