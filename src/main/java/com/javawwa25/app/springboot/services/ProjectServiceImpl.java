package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.repositories.ProjectRepository;


@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    
    public ProjectServiceImpl(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void save(Project project) {
        this.projectRepository.save(project);
    }

    @Override
    public Project getProjectById(long id) {
		return projectRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Project not found for id : " + id));
    }

    @Override
    public void deleteProjectById(long id) {
        this.projectRepository.deleteById(id);
    }


    @Override
    public List<Project> getAllProjectsByUserId(long userId) {
        List<Project> projectList = projectRepository.findByUsers_Id(userId);
        return projectList;
    }

}
