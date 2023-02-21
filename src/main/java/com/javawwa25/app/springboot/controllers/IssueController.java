package com.javawwa25.app.springboot.controllers;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.javawwa25.app.springboot.models.Priority;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserDto;

@Controller
@RequestMapping("/tasks")
public class IssueController {

	private final static String TASK_ENDPOINT = "/users/{userId}/projects/{projectId}/tasks";
	private final static Logger LOG = LoggerFactory.getLogger(IssueController.class);

	private final TaskService taskService;
	private final ProjectService projectService;
	private final UserService userService;

	public IssueController(TaskService taskService, ProjectService projectService, UserService userService) {
		this.taskService = taskService;
		this.projectService = projectService;
		this.userService = userService;
	}

	@GetMapping(TASK_ENDPOINT + "/new")
	public String createTask(@PathVariable(value = "projectId") long projectId,
			@PathVariable(value = "userId") long userId, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET createTask - called");
		Task task = new Task();
		Project project = projectService.getProjectById(projectId);
		User user = userService.getUserById(userId);
		model.addAttribute("user", user);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		return "/task/new_task";
	}

	@PostMapping(TASK_ENDPOINT + "/save")
	public String saveTask(@PathVariable(value = "userId") long userId,
			@PathVariable(value = "projectId") long projectId,
			@RequestParam("priority") Priority priority, @ModelAttribute("task") Task task) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - POST saveTask - called");
		task.setCreatedAt(new Date());
		task.setPriority(priority);
		task.setProject(projectService.getProjectById(projectId));
		task.setUserAdded(userService.getUserById(userId));

		taskService.saveTask(task);
		return "redirect:/users/" + userId;
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


	@GetMapping(TASK_ENDPOINT + "/{task_id}/movePrevious")
	public String moveTaskToPreviousStep(@PathVariable(value = "userId") long userId,
			@PathVariable(value = "projectId") long projectId,
			@PathVariable(value = "taskId") long taskId) {
		Task task = taskService.getTaskById(taskId);
		taskService.saveTask(task);
		return "redirect:/users/" + userId + "/projects/" + projectId + "/tasks";
	}
	@GetMapping(TASK_ENDPOINT)
	public String allTasks(@PathVariable("projectId") long projectId, @PathVariable("userId") long userId,
			Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET findAllProjectTasks - called");
		User user = userService.getUserById(userId);
		Project project = projectService.getProjectById(projectId);
		model.addAttribute("user", user);
		model.addAttribute("project", project);
		return "/task/task-list";
	}
	

}
