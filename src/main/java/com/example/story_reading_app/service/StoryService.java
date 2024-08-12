package com.example.story_reading_app.service;

import com.example.story_reading_app.dto.response.PaginatedResponse;
import com.example.story_reading_app.dto.request.PaginationRequest;
import com.example.story_reading_app.dto.response.StoryResponse;

import java.util.List;

public interface StoryService {
    PaginatedResponse<StoryResponse> getAllStories(PaginationRequest paginationRequest);

    StoryResponse getStoryById(Long id);

    StoryResponse createStory(StoryResponse storyResponse);

    StoryResponse updateStory(Long id, StoryResponse storyResponse);

    void deleteStory(Long id);

    List<StoryResponse> searchStories(String title, String author);

    List<StoryResponse> getFeaturedStories();

    List<StoryResponse> getRecentlyUpdatedStories();
}