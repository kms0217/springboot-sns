package com.kms.mygram.service;

import com.kms.mygram.domain.Authority;
import com.kms.mygram.domain.User;
import com.kms.mygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        HashSet<Authority> authorities = new HashSet<>();
        Authority defaultRole = new Authority(savedUser.getUserId(), "ROLE_USER");
        authorities.add(defaultRole);
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    public Optional<User> getUserByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}
