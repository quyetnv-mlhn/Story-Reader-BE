package com.example.story_reading_app.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "stories")
public class StoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String coverImage;
    private String publishedDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private Set<PageEntity> pages;
    private LocalDateTime lastUpdate;

    private boolean isFeatured;
    private int featuredOrder;   // Optional: controls the order of featured stories

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<PageEntity> getPages() {
        return pages;
    }

    public void setPages(Set<PageEntity> pages) {
        this.pages = pages;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public int getFeaturedOrder() {
        return featuredOrder;
    }

    public void setFeaturedOrder(int featuredOrder) {
        this.featuredOrder = featuredOrder;
    }
}