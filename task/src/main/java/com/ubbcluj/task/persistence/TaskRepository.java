package com.ubbcluj.task.persistence;

import com.ubbcluj.task.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("SELECT td FROM TaskEntity td WHERE td.assignedTo.username =: username")
    List<TaskEntity> findByCreatedBy(String username);
}
