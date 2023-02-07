package com.javawwa25.app.springboot.models;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Enumerated(EnumType.STRING)
    private Priority project_priority;

    // mapping projects with user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // mapping tasks with project
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="project")
    private Set<Task> project_tasks;



}
