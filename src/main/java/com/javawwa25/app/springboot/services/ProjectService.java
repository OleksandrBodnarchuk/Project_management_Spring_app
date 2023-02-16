package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Project;

@Service
public interface ProjectService {
    List<Project> getAllProjects();
    void save(Project project);
    Project getProjectById(long id);
    void deleteProjectById(long id);

    List<Project> getAllProjectsByUserId(long user_id);
}
