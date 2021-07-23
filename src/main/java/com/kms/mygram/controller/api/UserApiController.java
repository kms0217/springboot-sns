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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final ProfileEditValidator profileEditValidator;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> allUsers(){
        List<User> userList = userService.getAllUsers();
        if (userList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId){
        User user = userService.getUser(userId);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal Principal principal,
            @Valid ProfileEditDto profileEditDto,
            BindingResult bindingResult
    ){
        if (userId != principal.getUser().getUserId())
            throw new ApiForbiddenException("본인의 설정만 수정할 수 있습니다.");
        profileEditValidator.validate(profileEditDto, bindingResult);
        if (bindingResult.hasErrors()){
            StringBuffer buffer = new StringBuffer("Validation Fail\n");
            for(FieldError error : bindingResult.getFieldErrors())
                buffer.append(error.getDefaultMessage() + "\n");
            throw new ValidException(buffer.toString());
        }
        User userEntity = userService.updateUser(principal.getUser().getUserId(), profileEditDto);
        principal.setUser(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}
