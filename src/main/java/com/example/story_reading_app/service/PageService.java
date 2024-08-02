package com.example.story_reading_app.service;

import com.example.story_reading_app.entity.PageEntity;
import com.example.story_reading_app.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {
    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public List<PageEntity> getPagesByStoryId(Long storyId) {
        return pageRepository.findByStoryIdOrderByPageNumberAsc(storyId);
    }

    public PageEntity getPageById(Long id) {
        return pageRepository.findById(id).orElse(null);
    }

    public PageEntity savePage(PageEntity page) {
        return pageRepository.save(page);
    }

    public void deletePage(Long id) {
        pageRepository.deleteById(id);
    }
}
