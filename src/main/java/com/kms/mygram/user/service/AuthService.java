package com.kms.mygram.user.service;

import com.kms.mygram.user.entity.Authority;
import com.kms.mygram.user.entity.User;
import com.kms.mygram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${default.profile.image}")
    private String profileImageUrl;
    private final UserRepository userRepository;

    @Transactional
    public User signup(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setProfileImageUrl(profileImageUrl);
        User savedUser = userRepository.save(user);
        HashSet<Authority> authorities = new HashSet<>();
        Authority defaultRole = new Authority(savedUser.getUserId(), "ROLE_USER");
        authorities.add(defaultRole);
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}
