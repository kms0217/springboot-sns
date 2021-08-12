package com.kms.mygram.controller;

import com.kms.mygram.dto.UserRequestDto;
import com.kms.mygram.service.AuthService;
import com.kms.mygram.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserValidator userValidator;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/oauth-login-error")
    public String oAuthLoginError(Model model) {
        model.addAttribute("oauthError", true);
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        userValidator.validate(userRequestDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        authService.signup(userRequestDto.toEntity());
        return "redirect:/";
    }
}
