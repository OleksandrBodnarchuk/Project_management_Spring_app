package com.javawwa25.app.springboot.controllers;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ProjectController {
	
	private static final String PROJECTS_ENDPOINT = "/users/{userId}/projects";
	private final static Logger LOG = LoggerFactory.getLogger(ProjectController.class);
	
	private final  ProjectService projectService;
	private final  UserService userService;


	@GetMapping(PROJECTS_ENDPOINT)
    public String projectList(@PathVariable("userId") long userId, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET projectList - called");
		projectService.fillDtoProjectsModel(userId, model);
        return "project/project_list";
    }

	@Secured({"ADMIN"})
	@GetMapping(PROJECTS_ENDPOINT + "/add")
    public String showNewProjectForm(@PathVariable("userId") long userId, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showNewProjectForm - called");
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("project", new Project());
        return "project/new_project";
    }
	
	@GetMapping(PROJECTS_ENDPOINT + "/{projectId}/tasks")
	public String redirectToProjectTasks(@PathVariable("projectId") long projectId, @PathVariable("userId") long userId) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET redirectToProjectTasks - called");
		return "redirect:" + PROJECTS_ENDPOINT + "/" + projectId + "/tasks";
	}

	@Secured({"ADMIN"})
	@PostMapping(PROJECTS_ENDPOINT + "/save")
    public String saveProject(@PathVariable("userId") long userId, 
    		@ModelAttribute("project") Project project) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET saveProject - called");
        // Assigning current user to new project
        project.setStartDate(LocalDate.now());
        project.addUser(userService.getUserById(userId));
        projectService.save(project);
		return "redirect:" + PROJECTS_ENDPOINT;
    }

	@GetMapping(PROJECTS_ENDPOINT + "/{projectId}/update")
    public String showFormForUpdate(@PathVariable(value = "projectId") long projectId, Model model) {

        // get project from the service
        Project project = projectService.getProjectById(projectId);
        // set project as a model attribute to pre-populate the form
        model.addAttribute("project", project);
        return "project/update_project";
    }

}
