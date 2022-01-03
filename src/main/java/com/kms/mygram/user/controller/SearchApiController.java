package com.kms.mygram.user.controller;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.user.entity.User;
import com.kms.mygram.user.service.UserService;
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
        List<User> userList = userService.getUserWithFilter(principal.getUser(), filter);
        if (userList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(userService.getUserWithFilter(principal.getUser(), filter), HttpStatus.OK);
    }
}
