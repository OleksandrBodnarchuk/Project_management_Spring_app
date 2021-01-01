package com.javawwa25.app.springboot.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long project_id;

    private String project_name;

    private String project_info;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date project_startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date project_endDate;

    private Priority project_priority;

    // mapping projects with user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // mapping tasks with project
    @OneToMany(mappedBy="project")
    private Set<Task> project_tasks;




}
