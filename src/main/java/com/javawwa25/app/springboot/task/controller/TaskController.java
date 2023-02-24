package com.javawwa25.app.springboot.task.controller;

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

import com.javawwa25.app.springboot.project.service.ProjectService;
import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.task.dto.TaskDto;
import com.javawwa25.app.springboot.task.service.TaskService;
import com.javawwa25.app.springboot.user.dto.UserDto;
import com.javawwa25.app.springboot.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final static String TASK_ENDPOINT = "/projects/{projectId}";
	private final static Logger LOG = LoggerFactory.getLogger(TaskController.class);

	private final TaskService taskService;
	private final ProjectService projectService;
	private final UserService userService;

	@GetMapping(TASK_ENDPOINT)
	public String projectAllTasks(@PathVariable("projectId") long projectId, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET projectAllTasks - called");
		model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("projectId", projectId);
		model.addAttribute("taskList", taskService.getAllTasksByProjectId(projectId));
		return "/task/task-list";
	}
	
	@GetMapping(TASK_ENDPOINT + "/new")
	public String createTask(@RequestParam(value = "type") String type, @PathVariable(value = "projectId") long projectId, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET createTask - called");
		model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("projectName", projectService.getProjectNameById(projectId));
		model.addAttribute("projectId", projectId);
		model.addAttribute("users", userService.getSimpleDtos());
		model.addAttribute("task", TaskDto.builder().type(type).build());
		return "/task/new_task";
	}
	
	@PostMapping(TASK_ENDPOINT + "/new")
	public String saveTask(@PathVariable(value = "projectId") long projectId, @ModelAttribute("task") TaskDto dto) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - POST saveTask - called");
		dto.setProjectId(projectId);
		taskService.saveTask(dto);
		projectService.assignProject(dto.getUserAssignedId(), projectId);
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
