package com.javawwa25.app.springboot.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long task_id;

    private String comments;

    private String task_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date task_created;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date task_startDate;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date task_endDate;

    @Enumerated(EnumType.STRING)
    private Priority task_priority;

    @Enumerated(EnumType.STRING)
    private Progress task_progress;

    // Mapping Tasks with Project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    // mapping tasks with user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
