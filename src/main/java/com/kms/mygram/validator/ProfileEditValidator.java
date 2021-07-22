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
    public void validate(Object target, Errors errors) {
        ProfileEditDto profileEditDto = (ProfileEditDto) target;
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();
        String userPhone = user.getPhoneNumber();
        String userEmail = user.getEmail();
        String userUsername = user.getUsername();
        String validPhone = profileEditDto.getPhoneNumber();
        String validEmail = profileEditDto.getEmail();
        String validUsername = profileEditDto.getUsername();
        // email validation
        // 1. 현재 user의 email이 비어있을 때만 사용 -> 만약 가입할 때 Email을 입력했다면 이후 Email은 수정 불가
        // 2. 만약 user의 email이 비어 있다면 Email format이 맞는지 확인하고, 다른 중복된 User가 있는지 확인
        if (Utils.isBlank(userEmail) && !Utils.isBlank(validEmail)) {
            if (!EmailPhoneNumberValidator.isEmail(validEmail)) {
                errors.rejectValue("email", "key", "이메일 형식이 맞지 않습니다.");
            } else if (authService.getUserByEmail(validEmail).isPresent()) {
                errors.rejectValue("email", "key", "이미 존재하는 이메일 입니다.");
            }
        }
        // phoneNumber validation
        // 1. 현재 user의 phoneNumber가 비어있을 때만 사용 -> 만약 가입할 때 PhoneNumber 입력했다면 이후 PhoneNumber은 수정 불가
        // 2. 만약 user의 phoneNumber가 비어 있다면 PhoneNumber format이 맞는지 확인하고, 다른 중복된 User가 있는지 확인
        if (Utils.isBlank(userPhone) && !Utils.isBlank(validPhone)) {
            if (!EmailPhoneNumberValidator.isPhoneNumber(validPhone.replaceAll("[^0-9]", ""))) {
                errors.rejectValue("phoneNumber", "key", "핸드폰 번호 형식이 맞지 않습니다.");
            } else if (authService.getUserByPhoneNumber(validPhone).isPresent()) {
                errors.rejectValue("phoneNumber", "key", "이미 존재하는 휴대폰 번호 입니다.");
            }
        }
        // username validation
        // 1. 현재 User의 username과 같다면 검증 성공
        // 2. 빈값이 올 수 없음
        // 3. username format에 맞는지 확인
        // 4. 중복된 User가 있는지 확인
        if (!userUsername.equals(validUsername) && !Utils.isBlank(validUsername)) {
            if (authService.getUserByUserName(validUsername).isPresent()){
                errors.rejectValue("username", "key", "이미 존재하는 사용자 이름입니다.");
            } else{
                UserValidator.checkUserName(validUsername, errors);
            }
        }
    }
}
