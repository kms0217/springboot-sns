package com.kms.mygram.dto;

import com.kms.mygram.annotation.EmailPhoneNumber;
import com.kms.mygram.domain.User;
import com.kms.mygram.validator.EmailPhoneNumberValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    @Size(max = 20, message = "사용자 이름이 너무 깁니다.")
    @NotBlank(message = "사용자 이름을 입력해 주세요.")
    private String username;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    @NotBlank(message = "이메일 혹은 핸드폰 번호를 입력해 주세요.")
    @EmailPhoneNumber(message = "이메일 혹은 핸드폰 번호를 확인해주세요.")
    private String emailOrPhoneNumber;

    public User toEntity() {
        User user = new User();
        user.setUsername(this.username);
        user.setName(this.name);
        user.setPassword(this.password);
        if (EmailPhoneNumberValidator.isEmail(this.emailOrPhoneNumber)) {
            user.setEmail(this.emailOrPhoneNumber);
        } else {
            this.emailOrPhoneNumber = this.emailOrPhoneNumber.replaceAll("[^0-9]", "");
            user.setPhoneNumber(this.emailOrPhoneNumber);
        }
        return user;
    }
}
