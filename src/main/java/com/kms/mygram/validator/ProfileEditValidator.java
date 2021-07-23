package com.kms.mygram.validator;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.ProfileEditDto;
import com.kms.mygram.service.AuthService;
import com.kms.mygram.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfileEditValidator implements Validator {

    @Autowired
    private AuthService authService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProfileEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ProfileEditDto target = (ProfileEditDto) object;
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();

        emailValid(user.getEmail(), target.getEmail(), errors);
        phoneValid(user.getPhoneNumber(), target.getPhoneNumber(), errors);
        userNameValid(user.getUsername(), target.getUsername(), errors);
    }

    private void emailValid(String userEmail, String targetEmail, Errors errors) {
        if (Utils.isBlank(userEmail) && !Utils.isBlank(targetEmail)) {
            if (!EmailPhoneNumberValidator.isEmail(targetEmail)) {
                errors.rejectValue("email", "key", "이메일 형식이 맞지 않습니다.");
            } else if (authService.getUserByEmail(targetEmail).isPresent()) {
                errors.rejectValue("email", "key", "이미 존재하는 이메일 입니다.");
            }
        }
    }

    private void phoneValid(String userPhone, String targetPhone, Errors errors) {
        if (Utils.isBlank(userPhone) && !Utils.isBlank(targetPhone)) {
            if (!EmailPhoneNumberValidator.isPhoneNumber(targetPhone.replaceAll("[^0-9]", ""))) {
                errors.rejectValue("phoneNumber", "key", "핸드폰 번호 형식이 맞지 않습니다.");
            } else if (authService.getUserByPhoneNumber(targetPhone).isPresent()) {
                errors.rejectValue("phoneNumber", "key", "이미 존재하는 휴대폰 번호 입니다.");
            }
        }
    }

    private void userNameValid(String username, String targetUsername, Errors errors) {
        if (!username.equals(targetUsername) && !Utils.isBlank(targetUsername)) {
            if (authService.getUserByUserName(targetUsername).isPresent()) {
                errors.rejectValue("username", "key", "이미 존재하는 사용자 이름입니다.");
            } else {
                UserValidator.checkUserName(targetUsername, errors);
            }
        }
    }
}
