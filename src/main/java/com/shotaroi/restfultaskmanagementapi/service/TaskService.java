package com.shotaroi.restfultaskmanagementapi.service;

import com.shotaroi.restfultaskmanagementapi.dto.CreateTaskRequest;
import com.shotaroi.restfultaskmanagementapi.dto.PagedResponse;
import com.shotaroi.restfultaskmanagementapi.dto.TaskResponse;
import com.shotaroi.restfultaskmanagementapi.dto.UpdateTaskRequest;
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

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse create(CreateTaskRequest req) {
        Task task = new Task(req.getTitle());
        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public PagedResponse<TaskResponse> getAll(Boolean done, String q, Pageable pageable) {

        Page<Task> page;

        boolean hasDoneFilter = (done != null);
        boolean hasQuery = (q != null && !q.isBlank());

        if (hasDoneFilter && hasQuery) {
            page = taskRepository.findByDoneAndTitleContainingIgnoreCase(done, q, pageable);
        } else if (hasDoneFilter) {
            page = taskRepository.findByDone(done, pageable);
        } else if (hasQuery) {
            page = taskRepository.findByTitleContainingIgnoreCase(q, pageable);
        } else {
            page = taskRepository.findAll(pageable);
        }

        List<TaskResponse> items = page.getContent().stream()
                .map(this::toResponse)
                .toList();

        return new PagedResponse<>(
                items,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }



    public TaskResponse getOne(Long id) {
        Task task = taskRepository.findById(id)
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
