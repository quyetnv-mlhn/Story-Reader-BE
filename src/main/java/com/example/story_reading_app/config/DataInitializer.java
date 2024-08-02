package com.example.story_reading_app.config;

import com.example.story_reading_app.entity.StoryEntity;
import com.example.story_reading_app.repository.StoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StoryRepository storyRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DataInitializer(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run(String... args) throws Exception {
//        updateCoverImages();
    }

    private void updateCoverImages() throws JsonProcessingException {
        String url = "https://picsum.photos/v2/list?page=2&limit=100";
        String response = restTemplate.getForObject(url, String.class);

        JsonNode images = objectMapper.readTree(response);
        List<StoryEntity> stories = storyRepository.findAll();

        for (int i = 0; i < stories.size() && i < images.size(); i++) {
            JsonNode image = images.get(i);
            String imageUrl = image.get("download_url").asText();
            StoryEntity story = stories.get(i);
            story.setCoverImage(imageUrl);
            storyRepository.save(story);
        }

        System.out.println("Cover images updated successfully.");
    }
}