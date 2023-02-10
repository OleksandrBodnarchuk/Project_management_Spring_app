package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.ProjectRepository;


@Service
public class ProjectServiceImpl implements ProjectService{

	private final UserService userService;
    private final ProjectRepository projectRepository;
    
    public ProjectServiceImpl(UserService userService, ProjectRepository projectRepository) {
		this.userService = userService;
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
        List<Project> projectList = projectRepository.findAllUserProjects(userId);
        return projectList;
    }

	@Override
	public void fillUserProjects(long userId, Model model) {
		User user = userService.getUserById(userId);
        List<Project> projects = this.getAllProjectsByUserId(userId);
        model.addAttribute("user", user);
        model.addAttribute("projectList", projects);
	}
}
