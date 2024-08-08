package com.example.story_reading_app.repository;

import com.example.story_reading_app.entity.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<StoryEntity, Long> {
    List<StoryEntity> findByTitleContainingAndAuthorContaining(String title, String author);


    List<StoryEntity> findByTitleContaining(String title);

    List<StoryEntity> findByAuthorContaining(String author);
}
