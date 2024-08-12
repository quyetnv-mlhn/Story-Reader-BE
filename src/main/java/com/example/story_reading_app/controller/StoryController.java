package com.example.story_reading_app.controller;

import com.example.story_reading_app.dto.response.ApiResponse;
import com.example.story_reading_app.dto.response.PaginatedResponse;
import com.example.story_reading_app.dto.request.PaginationRequest;
import com.example.story_reading_app.dto.request.SearchRequest;
import com.example.story_reading_app.dto.response.StoryResponse;
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
    public ApiResponse<PaginatedResponse<StoryResponse>> getAllStories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        PaginationRequest paginationRequest = new PaginationRequest(page, pageSize);
        PaginatedResponse<StoryResponse> stories = storyService.getAllStories(paginationRequest);
        return ApiResponse.success(stories);
    }

    @GetMapping("/{id}")
    public ApiResponse<StoryResponse> getStoryById(@PathVariable Long id) {
        StoryResponse story = storyService.getStoryById(id);
        return ApiResponse.success(story);
    }

    @PostMapping
    public ApiResponse<StoryResponse> createStory(@RequestBody StoryResponse StoryResponse) {
        StoryResponse createdStory = storyService.createStory(StoryResponse);
        return ApiResponse.success(createdStory);
    }

    @PutMapping("/{id}")
    public ApiResponse<StoryResponse> updateStory(@PathVariable Long id, @RequestBody StoryResponse StoryResponse) {
        StoryResponse updatedStory = storyService.updateStory(id, StoryResponse);
        return ApiResponse.success(updatedStory);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/search")
    public ApiResponse<List<StoryResponse>> searchStories(SearchRequest criteria) {
        List<StoryResponse> stories = storyService.searchStories(criteria.getTitle(), criteria.getAuthor());
        return ApiResponse.success(stories);
    }

    @GetMapping("/featured")
    public ApiResponse<List<StoryResponse>> getFeaturedStories() {
        List<StoryResponse> featuredStories = storyService.getFeaturedStories();
        return ApiResponse.success(featuredStories);
    }

    @GetMapping("/recently-updated")
    public ApiResponse<List<StoryResponse>> getRecentlyUpdatedStories() {
        List<StoryResponse> recentlyUpdatedStories = storyService.getRecentlyUpdatedStories();
        return ApiResponse.success(recentlyUpdatedStories);
    }
}
