package com.works.repositories;

import com.works.entities.Task;
import com.works.utils.ETaskPriority;
import com.works.utils.ETaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatusEquals(ETaskStatus status, Pageable pageable);

    Page<Task> findByPriorityEquals(ETaskPriority priority, Pageable pageable);

    List<Task> findByAssignee_EmailEquals(String email);
    List<Task> findByAssignee_UidEquals(Long uid);
}