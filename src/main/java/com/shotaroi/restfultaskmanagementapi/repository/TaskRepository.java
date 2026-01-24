package com.shotaroi.restfultaskmanagementapi.repository;

import com.shotaroi.restfultaskmanagementapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
