package com.kms.mygram.service;

import com.kms.mygram.domain.User;
import com.kms.mygram.dto.ProfileEditDto;
import com.kms.mygram.repository.UserRepository;
import com.kms.mygram.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public User updateUser(Long userId, ProfileEditDto profileEditDto) {
        User user = userRepository.findById(userId).get();
        System.out.println(profileEditDto.toString());
        if (!Utils.isBlank(profileEditDto.getPhoneNumber()) && Utils.isBlank(user.getPhoneNumber()))
            user.setPhoneNumber(profileEditDto.getPhoneNumber());
        if (!Utils.isBlank(profileEditDto.getEmail()) && Utils.isBlank(user.getEmail()))
            user.setEmail((profileEditDto.getEmail()));
        if (!Utils.isBlank(profileEditDto.getUsername()))
            user.setUsername(profileEditDto.getUsername());
        user.setName(profileEditDto.getName());
        user.setGender(profileEditDto.getGender());
        user.setIntro(profileEditDto.getIntro());
        user.setWebsite(profileEditDto.getWebsite());
        return userRepository.save(user);
    }
}
