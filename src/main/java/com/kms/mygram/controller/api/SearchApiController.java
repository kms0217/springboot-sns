package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.User;
import com.kms.mygram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchApiController {

    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(@AuthenticationPrincipal Principal principal, @RequestParam String filter) {
        return new ResponseEntity<>(userService.getUserWithFilter(principal.getUser(), filter), HttpStatus.OK);
    }
}
