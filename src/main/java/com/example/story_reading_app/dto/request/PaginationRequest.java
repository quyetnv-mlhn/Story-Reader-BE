package com.example.story_reading_app.dto.request;

public class PaginationRequest {

    private int page;
    private int pageSize;

    public PaginationRequest() {
    }

    public PaginationRequest(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}