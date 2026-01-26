package com.shotaroi.restfultaskmanagementapi.controller;

import com.shotaroi.restfultaskmanagementapi.dto.CreateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.dto.PagedResponse;
import com.shotaroi.restfultaskmanagementapi.dto.TaskResponse;
import com.shotaroi.restfultaskmanagementapi.dto.UpdateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public PagedResponse<TaskResponse> getAll(
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false, name = "q") String q,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return taskService.getAll(done, q, pageable);
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
