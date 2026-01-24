package com.shotaroi.restfultaskmanagementapi.controller;

import com.shotaroi.restfultaskmanagementapi.dto.CreateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.dto.TaskResponse;
import com.shotaroi.restfultaskmanagementapi.dto.UpdateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest req) {
        return taskService.create(req);
    }

    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public TaskResponse getOne(@PathVariable Long id) {
        return taskService.getOne(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest req) {
        return taskService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
