package com.example.story_reading_app.service;

import com.example.story_reading_app.dto.StoryDTO;

import java.util.List;

public interface StoryService {
    List<StoryDTO> getAllStories();

    StoryDTO getStoryById(Long id);

    StoryDTO createStory(StoryDTO storyDTO);

    StoryDTO updateStory(Long id, StoryDTO storyDTO);

    void deleteStory(Long id);
}