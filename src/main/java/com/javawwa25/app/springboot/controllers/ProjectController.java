package com.javawwa25.app.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
	
	private final static Logger LOG = LoggerFactory.getLogger(ProjectController.class);
	
	private final  ProjectService projectService;
	private final  UserService userService;


	@GetMapping
    public String projectList(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET projectList - called");
		projectService.fillDtoProjectsModel(model);
        return "project/project_list";
    }

	@Secured({"ADMIN"})
	@GetMapping("/new")
    public String showNewProjectForm(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showNewProjectForm - called");
        model.addAttribute("user", userService.getLoggedUserDto());
        model.addAttribute("project", new Project());
        return "project/new_project";
    }
	
	@GetMapping("/{projectId}/tasks")
	public String redirectToProjectTasks(@PathVariable("projectId") long projectId) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET redirectToProjectTasks - called");
		return "redirect: /projects/" + projectId + "/tasks";
	}

	@Secured({"ADMIN"})
	@PostMapping("/save")
    public String saveProject(@ModelAttribute("project") Project project) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET saveProject - called");
        projectService.save(project);
		return "redirect: /admin/projects?success";
    }

	@GetMapping("/{projectId}/update")
    public String showFormForUpdate(@PathVariable(value = "projectId") long projectId, Model model) {

        // get project from the service
        Project project = projectService.getProjectById(projectId);
        // set project as a model attribute to pre-populate the form
        model.addAttribute("project", project);
        return "project/update_project";
    }

}
