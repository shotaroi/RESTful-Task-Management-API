package com.shotaroi.restfultaskmanagementapi.repository;

import com.shotaroi.restfultaskmanagementapi.entity.AppUser;
import com.shotaroi.restfultaskmanagementapi.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByOwner(AppUser owner, Pageable pageable);
    Page<Task> findByDone(boolean done, Pageable pageable);

    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Task> findByDoneAndTitleContainingIgnoreCase(boolean done, String title, Pageable pageable);

    Optional<Task> findByIdAndOwner(Long id, AppUser owner);

    boolean existsByIdAndOwner(Long id, AppUser owner);

}
