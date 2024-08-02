package com.example.story_reading_app.controller;

import com.example.story_reading_app.entity.PageEntity;
import com.example.story_reading_app.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/story/{storyId}")
    public List<PageEntity> getPagesByStoryId(@PathVariable Long storyId) {
        return pageService.getPagesByStoryId(storyId);
    }

    @GetMapping("/{id}")
    public PageEntity getPageById(@PathVariable Long id) {
        return pageService.getPageById(id);
    }

    @PostMapping
    public PageEntity createPage(@RequestBody PageEntity page) {
        return pageService.savePage(page);
    }

    @DeleteMapping("/{id}")
    public void deletePage(@PathVariable Long id) {
        pageService.deletePage(id);
    }
}
