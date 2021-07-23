package com.kms.mygram.service;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.StoryRequestDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.exception.ApiForbiddenException;
import com.kms.mygram.repository.StoryRepository;
import com.kms.mygram.utils.FileUploader;
import com.kms.mygram.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserService userService;
    private final FileUploader fileUploader;

    public List<Story> findAllStories() {
        return storyRepository.findAll();
    }

    public Story findById(Long storyId) {
        return storyRepository.findById(storyId).orElse(null);
    }

    @Transactional
    public Story createStory(StoryRequestDto storyRequestDto, User user) {
        String fileUrl = fileUploader.upload(storyRequestDto.getImage(), "post/");
        User userEntity = userService.getUser(user.getUserId());
        Story story = new Story();
        if (!Utils.isBlank(fileUrl))
            story.setImageUrl(fileUrl);
        story.setCaption(storyRequestDto.getCaption());
        story.setUser(userEntity);
        return storyRepository.save(story);
    }

    @Transactional
    public Story updateStory(Long storyId, StoryRequestDto storyRequestDto, User user) {
        Story story = storyRepository.findById(storyId).orElse(null);
        if (story == null)
            return null;
        if (story.getUser().getUserId() != user.getUserId())
            throw new ApiException("본인의 글이 아닙니다.");
        String fileUrl = fileUploader.upload(storyRequestDto.getImage(), "post/");
        story.setImageUrl(fileUrl);
        story.setCaption(storyRequestDto.getCaption());
        return storyRepository.save(story);
    }

    @Transactional
    public boolean deleteStory(Long storyId, User user) {
        Story story = storyRepository.findById(storyId).orElse(null);
        if (story == null)
            return false;
        if (story.getUser().getUserId() != user.getUserId())
            throw new ApiForbiddenException("본인의 글이 아닙니다.");
        return true;
    }
}
