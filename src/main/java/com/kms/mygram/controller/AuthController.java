package com.kms.mygram.controller;

import com.kms.mygram.dto.UserRequestDto;
import com.kms.mygram.service.AuthService;
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

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserRequestDto userRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "signup";
        } try {
            authService.signup(userRequestDto.toEntity());
        } catch (Exception e) {
            return "signup";
        }
        return "login";
    }
}
