package com.kms.mygram.service;

import com.kms.mygram.domain.User;
import com.kms.mygram.dto.ProfileEditDto;
import com.kms.mygram.repository.UserRepository;
import com.kms.mygram.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User updateUser(Long userId, ProfileEditDto profileEditDto) {
        User user = userRepository.findById(userId).get();
        if (!Utils.isBlank(profileEditDto.getPhoneNumber()) && Utils.isBlank(user.getPhoneNumber()))
            user.setPhoneNumber(profileEditDto.getPhoneNumber().replaceAll("[^0-9]", ""));
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
