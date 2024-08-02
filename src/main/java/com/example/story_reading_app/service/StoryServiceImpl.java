package com.example.story_reading_app.service;

import com.example.story_reading_app.dto.StoryDTO;
import com.example.story_reading_app.entity.Status;
import com.example.story_reading_app.entity.StoryEntity;
import com.example.story_reading_app.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    public StoryServiceImpl(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public List<StoryDTO> getAllStories() {
        return storyRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public StoryDTO getStoryById(Long id) {
        return storyRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    @Override
    public StoryDTO createStory(StoryDTO storyDTO) {
        StoryEntity story = convertToEntity(storyDTO);
        return convertToDTO(storyRepository.save(story));
    }

    @Override
    public StoryDTO updateStory(Long id, StoryDTO storyDTO) {
        StoryEntity story = storyRepository.findById(id).orElse(null);
        if (story != null) {
            story.setTitle(storyDTO.getTitle());
            story.setAuthor(storyDTO.getAuthor());
            story.setDescription(storyDTO.getDescription());
            story.setCoverImage(storyDTO.getCoverImage());
            story.setPublishedDate(storyDTO.getPublishedDate());
            story.setStatus(Status.valueOf(storyDTO.getStatus()));
            return convertToDTO(storyRepository.save(story));
        }
        return null;
    }

    @Override
    public void deleteStory(Long id) {
        storyRepository.deleteById(id);
    }

    private StoryDTO convertToDTO(StoryEntity story) {
        StoryDTO storyDTO = new StoryDTO();
        storyDTO.setId(story.getId());
        storyDTO.setTitle(story.getTitle());
        storyDTO.setAuthor(story.getAuthor());
        storyDTO.setDescription(story.getDescription());
        storyDTO.setCoverImage(story.getCoverImage());
        storyDTO.setPublishedDate(story.getPublishedDate());
        storyDTO.setStatus(String.valueOf(story.getStatus()));
        return storyDTO;
    }

    private StoryEntity convertToEntity(StoryDTO storyDTO) {
        StoryEntity story = new StoryEntity();
        story.setTitle(storyDTO.getTitle());
        story.setAuthor(storyDTO.getAuthor());
        story.setDescription(storyDTO.getDescription());
        story.setCoverImage(storyDTO.getCoverImage());
        story.setPublishedDate(storyDTO.getPublishedDate());
        story.setStatus(Status.valueOf(storyDTO.getStatus()));
        return story;
    }
}