package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.Story;
import com.kms.mygram.dto.StoryRequestDto;
import com.kms.mygram.exception.ValidException;
import com.kms.mygram.service.StoryService;
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
    public ResponseEntity<Page<Story>> allStoriesFilterByFollower(
            @AuthenticationPrincipal Principal principal,
            @PageableDefault(size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Story> storyPage = storyService.getFolloweeStoriesPage(principal.getUser(), pageable);
        return new ResponseEntity(storyPage, HttpStatus.OK);
    }

    @GetMapping("/explore")
    public ResponseEntity<Page<Story>> allStories(
            @PageableDefault(size = 20, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Story> storyPage = storyService.getStoriesPage(pageable);
        return new ResponseEntity<>(storyPage, HttpStatus.OK);
    }

    @GetMapping("/stories/{userId}")
    public ResponseEntity<Page<Story>> getTargetStory(
            @AuthenticationPrincipal Principal principal,
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Story> storyPage = storyService.getTargetStoriesPage(principal.getUser(), pageable);
        return new ResponseEntity<>(storyPage, HttpStatus.OK);
    }

    @PostMapping("/stories")
    public ResponseEntity createStory(
            @Valid StoryRequestDto storyRequestDto,
            BindingResult bindingResult,
            UriComponentsBuilder uriComponentsBuilder,
            @AuthenticationPrincipal Principal principal) {
        if (bindingResult.hasErrors())
            throw new ValidException("이미지 형식을 확인해주세요.");
        Story story = storyService.createStory(storyRequestDto, principal.getUser());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/stories/{storyId}").buildAndExpand(story.getStoryId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/stories/{storyId}")
    public ResponseEntity updateStory(
            @PathVariable Long storyId,
            @Valid @RequestBody StoryRequestDto storyRequestDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal Principal principal) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Story story = storyService.updateStory(storyId, storyRequestDto, principal.getUser());
        if (story == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(story, HttpStatus.OK);
    }

    @DeleteMapping("/stories/{storyId}")
    public ResponseEntity deleteStory(@PathVariable Long storyId, @AuthenticationPrincipal Principal principal) {
        storyService.deleteStory(storyId, principal.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }
}
