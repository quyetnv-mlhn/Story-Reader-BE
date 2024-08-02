package com.example.story_reading_app.repository;


import com.example.story_reading_app.entity.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<PageEntity, Long> {
    List<PageEntity> findByStoryIdOrderByPageNumberAsc(Long storyId);
}