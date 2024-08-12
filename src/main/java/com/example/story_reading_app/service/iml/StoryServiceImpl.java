package com.example.story_reading_app.service.iml;

import com.example.story_reading_app.dto.response.PaginatedResponse;
import com.example.story_reading_app.dto.request.PaginationRequest;
import com.example.story_reading_app.dto.response.StoryResponse;
import com.example.story_reading_app.entity.Status;
import com.example.story_reading_app.entity.StoryEntity;
import com.example.story_reading_app.exception.ResourceNotFoundException;
import com.example.story_reading_app.repository.StoryRepository;
import com.example.story_reading_app.service.StoryService;
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
    public PaginatedResponse<StoryResponse> getAllStories(PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getPageSize());
        Page<StoryEntity> storyPage = storyRepository.findAll(pageable);

        return new PaginatedResponse<>(
                storyPage.getContent().stream().map(this::convertToDTO).collect(Collectors.toList()),
                paginationRequest.getPage(),
                paginationRequest.getPageSize(),
                storyPage.getTotalElements(),
                storyPage.getTotalPages(),
                storyPage.hasNext()
        );
    }

    @Override
    public StoryResponse getStoryById(Long id) {
        return storyRepository.findById(id).map(this::convertToDTO).orElseThrow(() -> new ResourceNotFoundException("Story not found with ID: " + id));
    }

    @Override
    public StoryResponse createStory(StoryResponse storyResponse) {
        StoryEntity story = convertToEntity(storyResponse);
        return convertToDTO(storyRepository.save(story));
    }

    @Override
    public StoryResponse updateStory(Long id, StoryResponse storyResponse) {
        StoryEntity story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found with ID: " + id));
        if (story != null) {
            story.setTitle(storyResponse.getTitle());
            story.setAuthor(storyResponse.getAuthor());
            story.setDescription(storyResponse.getDescription());
            story.setCoverImage(storyResponse.getCoverImage());
            story.setPublishedDate(storyResponse.getPublishedDate());
            story.setStatus(Status.valueOf(storyResponse.getStatus()));
            return convertToDTO(storyRepository.save(story));
        }
        return null;
    }

    @Override
    public void deleteStory(Long id) {
        storyRepository.deleteById(id);
    }

    @Override
    public List<StoryResponse> searchStories(String title, String author) {
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

    public List<StoryResponse> getFeaturedStories() {
        List<StoryEntity> featuredStories = storyRepository.findTop5ByIsFeaturedTrueOrderByFeaturedOrderAsc();
        return featuredStories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<StoryResponse> getRecentlyUpdatedStories() {
        List<StoryEntity> recentStories = storyRepository.findTop10ByOrderByLastUpdateDesc();
        return recentStories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private StoryResponse convertToDTO(StoryEntity story) {
        StoryResponse storyResponse = new StoryResponse();
        storyResponse.setId(story.getId());
        storyResponse.setTitle(story.getTitle());
        storyResponse.setAuthor(story.getAuthor());
        storyResponse.setDescription(story.getDescription());
        storyResponse.setCoverImage(story.getCoverImage());
        storyResponse.setPublishedDate(story.getPublishedDate());
        storyResponse.setStatus(String.valueOf(story.getStatus()));
        return storyResponse;
    }

    private StoryEntity convertToEntity(StoryResponse storyResponse) {
        StoryEntity story = new StoryEntity();
        story.setTitle(storyResponse.getTitle());
        story.setAuthor(storyResponse.getAuthor());
        story.setDescription(storyResponse.getDescription());
        story.setCoverImage(storyResponse.getCoverImage());
        story.setPublishedDate(storyResponse.getPublishedDate());
        story.setStatus(Status.valueOf(storyResponse.getStatus()));
        return story;
    }
}