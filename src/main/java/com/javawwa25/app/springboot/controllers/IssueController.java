package com.javawwa25.app.springboot.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.TaskDto;
import com.javawwa25.app.springboot.web.dto.UserDto;

@Controller
@RequestMapping("/tasks")
public class IssueController {

	private final static String TASK_ENDPOINT = "/projects/{projectId}";
	private final static Logger LOG = LoggerFactory.getLogger(IssueController.class);

	private final TaskService taskService;
	private final ProjectService projectService;
	private final UserService userService;

	public IssueController(TaskService taskService, ProjectService projectService, UserService userService) {
		this.taskService = taskService;
		this.projectService = projectService;
		this.userService = userService;
	}

	@GetMapping(TASK_ENDPOINT)
	public String projectAllTasks(@PathVariable("projectId") long projectId, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET findAllProjectTasks - called");
		model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("project", projectService.getProjectDtoById(projectId));
		return "/task/task-list";
	}
	
	@GetMapping(TASK_ENDPOINT + "/new")
	public String createTask(@PathVariable(value = "projectId") long projectId, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET createTask - called");
		model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("projectName", projectService.getProjectNameById(projectId));
		model.addAttribute("projectId", projectId);
		model.addAttribute("users", userService.getDtoUserNames());
		model.addAttribute("task", TaskDto.builder().build());
		return "/task/new_task";
	}
	
	@PostMapping(TASK_ENDPOINT + "/new")
	public String saveTask(@PathVariable(value = "projectId") long projectId, @ModelAttribute("task") TaskDto dto) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - POST saveTask - called");
		dto.setProjectId(projectId);
		taskService.saveTask(dto);
		return "redirect:/tasks"+TASK_ENDPOINT+"?success";
	}

	@GetMapping(TASK_ENDPOINT + "/{taskId}")
	public String showTaskInfo(@PathVariable(value = "taskId") long taskId, Model model) {
		Task task = taskService.getTaskById(taskId);
		model.addAttribute("task", task);
		return "/task/task-info";
	}

	@GetMapping(TASK_ENDPOINT + "{taskId}/update")
	public String updateTask(@PathVariable(value = "userId") long userId,
			@PathVariable(value = "projectId") long projectId, @PathVariable(value = "taskId") long taskId,
			Model model) {
		Task task = taskService.getTaskById(taskId);
		List<UserDto> userList = userService.getAllUserDtos();
		model.addAttribute("userList", userList);
		model.addAttribute("task", task);
		return "/task/update_task";
	}

	@GetMapping(TASK_ENDPOINT + "/{task_id}/moveNext")
	public String moveTaskToNextStep(@PathVariable(value = "userId") long userId,
			@PathVariable(value = "projectId") long projectId,
			@PathVariable(value = "taskId") long taskId) {
		taskService.saveTask(null);
		return "redirect:/users/" + userId + "/projects/" + projectId + "/tasks";
	}

}
