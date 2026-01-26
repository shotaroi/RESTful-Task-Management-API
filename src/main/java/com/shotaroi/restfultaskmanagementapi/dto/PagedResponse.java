package com.shotaroi.restfultaskmanagementapi.dto;

import java.util.List;

public class PagedResponse <T> {

    private List<T> items;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public PagedResponse() {}

    public PagedResponse(List<T> items, int page, int size, long totalItems, int totalPages, boolean hasNext, boolean hasPrevious) {
        this.items = items;
        this.page = page;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public List<T> getItems() { return items; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }
    public boolean isHasNext() { return hasNext; }
    public boolean isHasPrevious() { return hasPrevious; }

}
