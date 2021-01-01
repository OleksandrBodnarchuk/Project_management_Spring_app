package com.javawwa25.app.springboot.models;

import lombok.*;

import javax.persistence.*;

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

    private String current_sprint;

    private Priority task_priority;

    private Progress task_progress;

    // Mapping Tasks with Project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // mapping User with Task


}
