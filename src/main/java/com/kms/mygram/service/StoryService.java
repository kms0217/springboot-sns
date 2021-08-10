package com.kms.mygram.service;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.StoryRequestDto;
import com.kms.mygram.dto.StoryResponseDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.exception.ApiForbiddenException;
import com.kms.mygram.repository.StoryRepository;
import com.kms.mygram.utils.FileUploader;
import com.kms.mygram.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserService userService;
    private final FileUploader fileUploader;
    private final EntityManager em;

    @Transactional(readOnly = true)
    public Story getStoryById(Long storyId) {
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
    public void updateStory(Long storyId, StoryRequestDto storyRequestDto, User user) {
        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new ApiException("존재하지 않는 Story 입니다."));
        if (!user.getUserId().equals(story.getUser().getUserId()))
            throw new ApiException("본인의 글이 아닙니다.");
        String fileUrl = fileUploader.upload(storyRequestDto.getImage(), "post/");
        story.setImageUrl(fileUrl);
        story.setCaption(storyRequestDto.getCaption());
        storyRepository.save(story);
    }

    @Transactional
    public void deleteStory(Long storyId, User user) {
        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new ApiException("존재하지 않는 Story 입니다."));
        if (!user.getUserId().equals(story.getUser().getUserId()))
            throw new ApiForbiddenException("본인의 글이 아닙니다.");
    }

    @Transactional(readOnly = true)
    public List<Story> getAllStoriesByUser(User user) {
        return storyRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Transactional(readOnly = true)
    public Page<Story> getFolloweeStoriesPage(User user, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        int total = storyRepository.countFolloweeStories(user);
        List<Story> stories = em.createQuery("select distinct s from Story s where s.user in (select f.followee from Follow f where f.follower=:user)", Story.class)
                .setParameter("user", user)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        stories = em.createQuery("select distinct s from Story s join fetch s.comments c join fetch c.user where s in :stories", Story.class)
                .setParameter("stories", stories)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        stories = em.createQuery("select distinct s from Story s join fetch s.likes l join fetch l.user where s in :stories", Story.class)
                .setParameter("stories", stories)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        stories.forEach(story ->
                story.getLikes().forEach(like -> {
                    if (user.getUserId().equals(like.getUser().getUserId()))
                        story.setLikeStatus(true);
                })
        );
        return new PageImpl<>(stories, pageable, total);
    }

    @Transactional(readOnly = true)
    public Page<StoryResponseDto> getTargetStoriesPage(Long userId, Pageable pageable) {
        Page<Story> storyPage = storyRepository.findByUser(userService.getUserById(userId), pageable);
        return toPageStoryResponseDto(storyPage);
    }

    @Transactional(readOnly = true)
    public Page<StoryResponseDto> getStoriesPage(Pageable pageable) {
        Page<Story> storyPage = storyRepository.findAll(pageable);
        return toPageStoryResponseDto(storyPage);
    }

    private Page<StoryResponseDto> toPageStoryResponseDto(Page<Story> storyPage) {
        return storyPage.map(this::convertToStoryResponseDto);
    }

    private StoryResponseDto convertToStoryResponseDto(Story story) {
        return StoryResponseDto.builder()
                .storyId(story.getStoryId())
                .caption(story.getCaption())
                .commentNum(story.getCommentNum())
                .imageUrl(story.getImageUrl())
                .likeNum(story.getLikeNum())
                .user(story.getUser())
                .build();
    }
}
