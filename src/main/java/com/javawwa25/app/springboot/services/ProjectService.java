package com.javawwa25.app.springboot.services;

import com.javawwa25.app.springboot.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    List<Project> getAllProjects();
    void saveProject(Project project);
    Project getProjectById(long id);
    void deleteProjectById(long id);
    Page<Project> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
