package com.ubbcluj.authentication.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
@SecondaryTable(name = "authentication_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(table = "authentication_details")
    private String password;
}