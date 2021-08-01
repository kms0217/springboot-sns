package com.kms.mygram.service;

import com.kms.mygram.domain.Like;
import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.StoryRequestDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.exception.ApiForbiddenException;
import com.kms.mygram.repository.StoryRepository;
import com.kms.mygram.utils.FileUploader;
import com.kms.mygram.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return storyRepository.findById(storyId).orElseThrow(() ->
                new ApiException("존재하지 않는 Story 입니다."));
    }

    @Transactional
    public Story createStory(StoryRequestDto storyRequestDto, User user) {
        String fileUrl = fileUploader.upload(storyRequestDto.getImage(), "post/");
        User userEntity = userService.getUserById(user.getUserId());
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
        if (!user.getUserId().equals(story.getUser().getUserId()))
            throw new ApiException("본인의 글이 아닙니다.");
        String fileUrl = fileUploader.upload(storyRequestDto.getImage(), "post/");
        story.setImageUrl(fileUrl);
        story.setCaption(storyRequestDto.getCaption());
        return storyRepository.save(story);
    }

    @Transactional
    public void deleteStory(Long storyId, User user) {
        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new ApiException("존재하지 않는 Story 입니다.")
        );
        if (!user.getUserId().equals(story.getUser().getUserId()))
            throw new ApiForbiddenException("본인의 글이 아닙니다.");
    }

    public List<Story> findAllStoriesByUser(User user) {
        return storyRepository.findAllByUserId(user.getUserId());
    }

    public Page<Story> getFolloweeStoriesPage(User user, Pageable pageable) {
        Page<Story> storyPage = storyRepository.getFolloweeStories(user.getUserId(), pageable);
        for (Story story : storyPage) {
            List<Like> likes = story.getLikes();
            story.setLikeNum(likes.size());
            for (Like like : likes) {
                if (user.getUserId().equals(like.getUser().getUserId())) {
                    story.setLikeStatus(true);
                    break;
                }
            }
        }
        return storyPage;
    }

    public Page<Story> getTargetStoriesPage(Long userId, Pageable pageable) {
        Page<Story> storyPage = storyRepository.getTargetStories(userId, pageable);
        for (Story story : storyPage) {
            story.setLikeNum(story.getLikes().size());
            story.setCommentNum(story.getComments().size());
        }
        return storyPage;
    }

    public Page<Story> getStoriesPage(Pageable pageable) {
        Page<Story> storyPage = storyRepository.findAll(pageable);
        for (Story story : storyPage) {
            story.setLikeNum(story.getLikes().size());
            story.setCommentNum(story.getComments().size());
        }
        return storyPage;
    }
}
