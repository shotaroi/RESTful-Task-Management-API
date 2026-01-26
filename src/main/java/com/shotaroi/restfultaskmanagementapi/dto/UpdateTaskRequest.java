package com.shotaroi.restfultaskmanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateTaskRequest {

    @NotBlank(message = "title must not be blank")
    @Size(max = 255, message = "title must be at most 255 characters")
    private String title;

    private boolean done;

    public UpdateTaskRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
}
