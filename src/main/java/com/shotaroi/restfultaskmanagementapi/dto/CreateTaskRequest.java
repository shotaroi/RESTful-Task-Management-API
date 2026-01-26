package com.shotaroi.restfultaskmanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {

    @NotBlank(message = "title must not be blank")
    @Size(max = 255, message = "title must be at most 255 characters")
    private String title;

    public CreateTaskRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
