package com.ubbcluj.task.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ubbcluj.task.persistence.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @OneToOne
    @JoinColumn(name = "created_by")
    @JsonBackReference
    private UserEntity createdBy;

    @OneToOne
    @JoinColumn(name = "assigned_to")
    @JsonBackReference
    private UserEntity assignedTo;
}
