package com.kms.mygram.service;

import com.kms.mygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsernameOrEmailOrPhoneNumber(username, username, username.replaceAll("[^0-9]", ""))
                .orElseThrow(()->new UsernameNotFoundException("존재하지 않는 사용자 입니다."));
    }
}
