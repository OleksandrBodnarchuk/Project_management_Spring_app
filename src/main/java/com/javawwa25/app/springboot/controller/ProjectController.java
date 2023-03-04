package com.javawwa25.app.springboot.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.project.Project;
import com.javawwa25.app.springboot.project.dto.ProjectDto;
import com.javawwa25.app.springboot.project.service.ProjectService;
import com.javawwa25.app.springboot.user.dto.UserDto;
import com.javawwa25.app.springboot.user.service.UserService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
	
	private final  ProjectService projectService;
	private final  UserService userService;

	@GetMapping
    public String projectList(Model model) {
		UserDto dto = userService.getLoggedUserDto();
		model.addAttribute("user", dto);
		model.addAttribute("projectList", projectService.getProjectDtosByAccountId(dto.getAccountId()));
        return "project/project_list";
    }
	
	@GetMapping("/{projectId}")
    public String projectPage(@PathVariable(value = "projectId") long projectId, Model model) {
		UserDto dto = userService.getLoggedUserDto();
		model.addAttribute("user", dto);
		model.addAttribute("project", projectService.getProjectDtoById(projectId));
        return "project/project_page";
    }

	@Secured({"ADMIN"})
	@GetMapping("/new")
    public String showNewProjectForm(Model model) {
        model.addAttribute("user", userService.getLoggedUserDto());
        model.addAttribute("project", ProjectDto.builder().build());
        return "project/new_project";
    }
	
	@GetMapping("/{projectId}/tasks")
	public String redirectToProjectTasks(@PathVariable("projectId") long projectId) {
		return "redirect:/tasks/projects/" + projectId;
	}
	
	@Secured({"ADMIN"})
	@PostMapping("/save")
    public String saveProject(@ModelAttribute("project") ProjectDto dto) {
        projectService.save(dto);
		return "redirect:/admin/projects?success";
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
