package com.shotaroi.restfultaskmanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;

public class CreateTaskRequest {

    @NotBlank(message = "title must not be blank")
    private String title;

    public CreateTaskRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
