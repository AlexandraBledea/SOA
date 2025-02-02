package com.ubbcluj.task.persistence;

import com.ubbcluj.task.persistence.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Integer> {
    List<AssignmentEntity> findByAssignedTo(Long id);
}
