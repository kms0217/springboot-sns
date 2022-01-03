package com.kms.mygram.user.service;

import com.kms.mygram.user.entity.User;
import com.kms.mygram.user.dto.ProfileEditDto;
import com.kms.mygram.common.exception.ApiException;
import com.kms.mygram.user.repository.UserRepository;
import com.kms.mygram.common.utils.FileUploader;
import com.kms.mygram.common.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileUploader fileUploader;

    @Transactional
    public User updateUser(Long userId, ProfileEditDto profileEditDto) {
        String fileUrl = fileUploader.upload(profileEditDto.getImage(), "profile/");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ApiException("존재하지 않는 User 입니다.")
        );
        if (!Utils.isBlank(profileEditDto.getPhoneNumber()) && Utils.isBlank(user.getPhoneNumber()))
            user.setPhoneNumber(profileEditDto.getPhoneNumber().replaceAll("[^0-9]", ""));
        if (!Utils.isBlank(profileEditDto.getEmail()) && Utils.isBlank(user.getEmail()))
            user.setEmail((profileEditDto.getEmail()));
        if (!Utils.isBlank(profileEditDto.getUsername()))
            user.setUsername(profileEditDto.getUsername());
        if (!Utils.isBlank(fileUrl))
            user.setProfileImageUrl(fileUrl);
        user.setName(profileEditDto.getName());
        user.setGender(profileEditDto.getGender());
        user.setIntro(profileEditDto.getIntro());
        user.setWebsite(profileEditDto.getWebsite());
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ApiException("존재하지 않는 User 입니다.")
        );
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new ApiException("존재하지 않는 User 입니다.")
        );
    }

    public List<User> getRecommendUsers(Long userId) {
        //TODO 이후 어떤 추천 알고리즘 사용할지 생각, 현재는 Follow 안한 user
        return userRepository.findRecommendUser(userId);
    }

    public List<User> getUserWithFilter(User user, String filter) {
        return userRepository.findWithFilter(user.getUserId(), filter);
    }
}
