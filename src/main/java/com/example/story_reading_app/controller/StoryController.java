package com.example.story_reading_app.controller;

import com.example.story_reading_app.dto.PaginatedResponse;
import com.example.story_reading_app.dto.PaginationRequest;
import com.example.story_reading_app.dto.SearchRequest;
import com.example.story_reading_app.dto.StoryDTO;
import com.example.story_reading_app.service.StoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public PaginatedResponse<StoryDTO> getAllStories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        return storyService.getAllStories(paginationRequest);
    }

    @GetMapping("/{id}")
    public StoryDTO getStoryById(@PathVariable Long id) {
        return storyService.getStoryById(id);
    }

    @PostMapping
    public StoryDTO createStory(@RequestBody StoryDTO storyDTO) {
        return storyService.createStory(storyDTO);
    }

    @PutMapping("/{id}")
    public StoryDTO updateStory(@PathVariable Long id, @RequestBody StoryDTO storyDTO) {
        return storyService.updateStory(id, storyDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
    }

    @GetMapping("/search")
    public List<StoryDTO> searchStories(SearchRequest criteria) {
        return storyService.searchStories(criteria.getTitle(), criteria.getAuthor());
    }
}
