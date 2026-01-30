package com.shotaroi.restfultaskmanagementapi.service;

import com.shotaroi.restfultaskmanagementapi.dto.CreateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.dto.PagedResponse;
import com.shotaroi.restfultaskmanagementapi.dto.TaskResponse;
import com.shotaroi.restfultaskmanagementapi.dto.UpdateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.entity.AppUser;
import com.shotaroi.restfultaskmanagementapi.entity.Task;
import com.shotaroi.restfultaskmanagementapi.exception.NotFoundException;
import com.shotaroi.restfultaskmanagementapi.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final com.shotaroi.restfultaskmanagementapi.repository.UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, com.shotaroi.restfultaskmanagementapi.repository.UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse create(CreateTaskRequest req) {
        String username = com.shotaroi.restfultaskmanagementapi.security.AuthUtil.currentUsername();
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
        Task task = new Task(req.getTitle());
        task.setOwner(owner);

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public Page<TaskResponse> getAll(int page, int size, String sortBy, String direction) {
        String username = com.shotaroi.restfultaskmanagementapi.security.AuthUtil.currentUsername();
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return taskRepository.findByOwner(owner, pageable).map(this::toResponse);
    }




    public TaskResponse getOne(Long id) {
        String username = com.shotaroi.restfultaskmanagementapi.security.AuthUtil.currentUsername();
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));

        Task task = taskRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new NotFoundException("Task " + id + " not found"));

        return toResponse(task);
    }


    public TaskResponse update(Long id, UpdateTaskRequest req) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task " + id + " not found"));

        task.setTitle(req.getTitle());
        task.setDone(req.isDone());

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task " + id + " not found");
        }
        taskRepository.deleteById(id);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.isDone(),
                task.getCreatedAt()
        );
    }
}
