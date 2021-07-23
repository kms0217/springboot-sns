package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.Story;
import com.kms.mygram.dto.StoryRequestDto;
import com.kms.mygram.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoryApiController {

    private final StoryService storyService;

    @GetMapping("/stories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Story>> allStories(){
        List<Story> storyList = storyService.findAllStories();
        if (storyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(storyList, HttpStatus.OK);
    }

    @GetMapping("/stories/{storyId}")
    public ResponseEntity<Story> getStory(@PathVariable Long storyId){
        Story story = storyService.findById(storyId);
        if (story == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(story, HttpStatus.OK);
    }

    @PostMapping("/stories")
    public ResponseEntity createStory(
            @Valid StoryRequestDto storyRequestDto,
            BindingResult bindingResult,
            UriComponentsBuilder uriComponentsBuilder,
            @AuthenticationPrincipal Principal principal)
    {
        if (bindingResult.hasErrors())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
            @AuthenticationPrincipal Principal principal)
    {
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
