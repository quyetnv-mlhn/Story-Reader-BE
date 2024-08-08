package com.example.story_reading_app.service;

import com.example.story_reading_app.dto.PaginatedResponse;
import com.example.story_reading_app.dto.PaginationRequest;
import com.example.story_reading_app.dto.StoryDTO;
import com.example.story_reading_app.entity.Status;
import com.example.story_reading_app.entity.StoryEntity;
import com.example.story_reading_app.repository.StoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PaginatedResponse<StoryDTO> getAllStories(PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize());
        Page<StoryEntity> storyPage = storyRepository.findAll(pageable);

        return new PaginatedResponse<>(
                storyPage.getContent().stream().map(this::convertToDTO).collect(Collectors.toList()),
                paginationRequest.getPage(),
                paginationRequest.getSize(),
                storyPage.getTotalElements(),
                storyPage.getTotalPages(),
                storyPage.hasNext()
        );
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

    @Override
    public List<StoryDTO> searchStories(String title, String author) {
        List<StoryEntity> stories;
        if (title != null && author != null) {
            stories = storyRepository.findByTitleContainingAndAuthorContaining(title, author);
        } else if (title != null) {
            stories = storyRepository.findByTitleContaining(title);
        } else if (author != null) {
            stories = storyRepository.findByAuthorContaining(author);
        } else {
            stories = storyRepository.findAll();
        }
        return stories.stream().map(this::convertToDTO).collect(Collectors.toList());
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