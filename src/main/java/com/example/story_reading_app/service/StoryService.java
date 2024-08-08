package com.example.story_reading_app.service;

import com.example.story_reading_app.dto.PaginatedResponse;
import com.example.story_reading_app.dto.PaginationRequest;
import com.example.story_reading_app.dto.StoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoryService {
    PaginatedResponse<StoryDTO> getAllStories(PaginationRequest paginationRequest);

    StoryDTO getStoryById(Long id);

    StoryDTO createStory(StoryDTO storyDTO);

    StoryDTO updateStory(Long id, StoryDTO storyDTO);

    void deleteStory(Long id);

    List<StoryDTO> searchStories(String title, String author);
}