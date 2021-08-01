package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.ProfileEditDto;
import com.kms.mygram.exception.ApiForbiddenException;
import com.kms.mygram.exception.ValidException;
import com.kms.mygram.service.UserService;
import com.kms.mygram.validator.ProfileEditValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final ProfileEditValidator profileEditValidator;

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal Principal principal) {
        return new ResponseEntity<>(principal.getUser(), HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public HttpStatus updateUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal Principal principal,
            @Valid ProfileEditDto profileEditDto,
            BindingResult bindingResult
    ) {
        if (!principal.getUser().getUserId().equals(userId))
            throw new ApiForbiddenException("본인의 설정만 수정할 수 있습니다.");
        profileEditValidator.validate(profileEditDto, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuffer buffer = new StringBuffer("Validation Fail\n");
            for (FieldError error : bindingResult.getFieldErrors())
                buffer.append(error.getDefaultMessage() + "\n");
            throw new ValidException(buffer.toString());
        }
        User userEntity = userService.updateUser(principal.getUser().getUserId(), profileEditDto);
        principal.setUser(userEntity);
        return HttpStatus.OK;
    }
}
