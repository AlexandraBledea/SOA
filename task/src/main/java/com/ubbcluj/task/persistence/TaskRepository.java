package com.ubbcluj.task.persistence;

import com.ubbcluj.task.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByCreatedBy(String username);
}
