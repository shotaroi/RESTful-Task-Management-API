package com.shotaroi.restfultaskmanagementapi.controller;

import com.shotaroi.restfultaskmanagementapi.dto.CreateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.dto.PagedResponse;
import com.shotaroi.restfultaskmanagementapi.dto.TaskResponse;
import com.shotaroi.restfultaskmanagementapi.dto.UpdateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.entity.AppUser;
import com.shotaroi.restfultaskmanagementapi.repository.UserRepository;
import com.shotaroi.restfultaskmanagementapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks", description = "Create, read, update, and delete tasks")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Create a new task")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        AppUser owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found:" + username));
        return taskService.create(req);
    }

    @Operation(summary = "Get all tasks")
    @GetMapping
    public Page<TaskResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        // Guardrails
        if (page < 0) page = 0;
        if (size < 1) size = 1;
        if (size > 100) size = 100;

        // Allow-list of safe sort fields (must match Task entity field names!)
        if (!sortBy.equals("createdAt") && !sortBy.equals("title") && !sortBy.equals("done") && !sortBy.equals("id")) {
            sortBy = "createdAt";
        }

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return taskService.getAll(page, size, sortBy, direction);
    }



    @Operation(summary = "Get a task")
    @GetMapping("/{id}")
    public TaskResponse getOne(@PathVariable Long id) {
        return taskService.getOne(id);
    }

    @Operation(summary = "Update a task")
    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest req) {
        return taskService.update(id, req);
    }


    @Operation(summary = "Delete a task")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
