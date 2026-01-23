package com.shotaroi.restfultaskmanagementapi.dto;

import java.time.Instant;

public class TaskResponse {
    private Long id;
    private String title;
    private boolean done;
    private Instant createdAt;

    public TaskResponse() {}

    public TaskResponse(Long id, String title, boolean done, Instant createdAT) {
        this.id = id;
        this.title = title;
        this.done = done;
        this.createdAt = createdAT;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isDone() { return done; }
    public Instant getCreatedAt() { return createdAt; }
}
