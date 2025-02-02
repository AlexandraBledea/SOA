package com.ubbcluj.task.persistence.entity;

import com.ubbcluj.task.persistence.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;
}
