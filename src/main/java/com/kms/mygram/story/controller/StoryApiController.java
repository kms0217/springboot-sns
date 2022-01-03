package com.kms.mygram.story.controller;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.story.entity.Story;
import com.kms.mygram.story.dto.StoryRequestDto;
import com.kms.mygram.story.dto.StoryResponseDto;
import com.kms.mygram.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoryApiController {

    private final StoryService storyService;

    @GetMapping("/stories")
    public ResponseEntity<Page<StoryResponseDto>> getAllStoriesPage(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<StoryResponseDto> storyPage = storyService.getStoriesPage(pageable);
        return new ResponseEntity<>(storyPage, HttpStatus.OK);
    }

    @GetMapping("/stories/home")
    public ResponseEntity<Page<Story>> getAllFolloweeStoriesPage(@AuthenticationPrincipal Principal principal,
                                                                 @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Story> storyPage = storyService.getFolloweeStoriesPage(principal.getUser(), pageable);
        return new ResponseEntity<>(storyPage, HttpStatus.OK);
    }

    @GetMapping("/stories/{storyId}")
    public ResponseEntity<Story> getStory(@PathVariable Long storyId) {
        Story story = storyService.getStoryById(storyId);
        return new ResponseEntity<>(story, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/stories")
    public ResponseEntity<Page<StoryResponseDto>> getAllStoriesByUser(@PathVariable Long userId,
                                                                      @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<StoryResponseDto> storyPage = storyService.getTargetStoriesPage(userId, pageable);
        return new ResponseEntity<>(storyPage, HttpStatus.OK);
    }

    @PostMapping("/stories")
    public ResponseEntity<Void> createStory(@AuthenticationPrincipal Principal principal,
                                            @Valid StoryRequestDto storyRequestDto,
                                            BindingResult bindingResult,
                                            UriComponentsBuilder uriComponentsBuilder) {

        Story story = storyService.createStory(storyRequestDto, principal.getUser());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/api/stories/{storyId}").buildAndExpand(story.getStoryId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/stories/{storyId}")
    public HttpStatus updateStory(@AuthenticationPrincipal Principal principal,
                                  @PathVariable Long storyId,
                                  @Valid @RequestBody StoryRequestDto storyRequestDto,
                                  BindingResult bindingResult) {

        storyService.updateStory(storyId, storyRequestDto, principal.getUser());
        return HttpStatus.OK;
    }

    @DeleteMapping("/stories/{storyId}")
    public HttpStatus deleteStory(@AuthenticationPrincipal Principal principal, @PathVariable Long storyId) {
        storyService.deleteStory(storyId, principal.getUser());
        return HttpStatus.OK;
    }
}
