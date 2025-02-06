//package com.ubbcluj.task.persistence.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@Table(name = "notification")
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//public class NotificationEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne()
//    @JoinColumn(name = "receiver_id", nullable = false)
//    private UserEntity receiver;
//
//    @Column(name="send_date")
//    private LocalDateTime sendDate;
//
//    @Column(name="seen")
//    private Boolean seen;
//}
