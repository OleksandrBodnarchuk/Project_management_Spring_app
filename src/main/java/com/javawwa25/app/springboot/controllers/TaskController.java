package com.javawwa25.app.springboot.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javawwa25.app.springboot.models.Priority;
import com.javawwa25.app.springboot.models.Progress;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	private final static Logger LOG = LoggerFactory.getLogger(TaskController.class);

	private final TaskService taskService;
	private final ProjectService projectService;
	private final UserService userService;
	private final UserRepository userRepository;

	public TaskController(TaskService taskService, ProjectService projectService,
			UserService userService, UserRepository userRepository) {
		this.taskService = taskService;
		this.projectService = projectService;
		this.userService = userService;
		this.userRepository = userRepository;
	}

	// adding Task to specific project
	@GetMapping("/showNewTaskForm/{project_id}")
	public String showNewTaskForm(@PathVariable(value = "project_id") long project_id, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showNewTaskForm - called");
		Task task = new Task();
		Project project = projectService.getProjectById(project_id);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		return "/task/new_task";
	}

	@GetMapping("/new/project/{projectId}/user/{id}")
	public String createTask(@PathVariable(value = "projectId") long projectId, @PathVariable(value = "id") long userId,
			Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET createTask - called");
		Task task = new Task();
		Project project = projectService.getProjectById(projectId);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		return "/task/new_task";
	}

	@GetMapping("/new/project/{id}")
	public String createNewTask(@PathVariable(value = "id") long projectId,
			Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET createNewTask - called");
		Task task = new Task();
		Project project = projectService.getProjectById(projectId);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		return "/task/new_task";
	}
	
	@PostMapping("/save/project/{id}")
	public String saveTask(String userName, @PathVariable(value = "id") long id,
			@RequestParam("priority") Priority priority, @ModelAttribute("task") Task task) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - POST saveTask - called");
		Date date = new Date();
		task.setCreated(date);
		task.setProgress(Progress.NEW);
		task.setPriority(priority);
		// Assign current project to task
		task.setProject(projectService.getProjectById(id));
		// Assign current User to task
		task.setUser(userRepository.findByEmail(userName));

		taskService.saveTask(task);
		return "redirect:/user";
	}

	@GetMapping("/showTaskInfo/{task_id}")
	public String showTaskInfo(@PathVariable(value = "task_id") long task_id, Model model) {
		Task task = taskService.getTaskById(task_id);
		model.addAttribute("task", task);
		return "/task/task-info";
	}

	@GetMapping("/deleteTask/{task_id}")
	public String deleteTask(@PathVariable(value = "task_id") long task_id) {
		this.taskService.deleteTaskById(task_id);
		return "redirect:/user";
	}

	// Updating current task
	@GetMapping("/updateCurrentTask/{task_id}")
	public String updateTaskInBoard(@PathVariable(value = "task_id") long task_id, Model model) {
		Task task = taskService.getTaskById(task_id);
		List<User> userList = userService.getAllUsers();
		model.addAttribute("userList", userList);
		model.addAttribute("task", task);
		return "/task/update_task";
	}

	@PostMapping("/saveCurrentTask")
	public String saveCurrentTask(@ModelAttribute("task") Task newTask) {
		Task oldTaskFromDB = taskService.getTaskById(newTask.getId());
		newTask.setPriority(oldTaskFromDB.getPriority());
		newTask.setProgress(oldTaskFromDB.getProgress());
		newTask.setProject(oldTaskFromDB.getProject());
		newTask.setCreated(oldTaskFromDB.getCreated());
		taskService.saveTask(newTask);
		return "redirect:/user";
	}

	@GetMapping("/moveTaskToNextStep/{task_id}")
	public String moveTaskToNextStep(@PathVariable(value = "task_id") long task_id) {
		Task task = taskService.getTaskById(task_id);
		switch (task.getProgress()) {
		case NEW:
			task.setProgress(Progress.IN_PROGRESS);
			break;
		case IN_PROGRESS:
			task.setProgress(Progress.QA);
			break;
		case QA:
			task.setProgress(Progress.DONE);
			break;
		}
		long user_id = task.getUser().getId();
		taskService.saveTask(task);
		return "redirect:/task/allTasksByUserId/" + user_id;
	}

	@GetMapping("/allTasksByUser/{user_id}")
	public String taskList(@PathVariable(value = "user_id") long user_id, Model model) {
//        List<Task> taskList = taskRepository.getAllTasksByUserId(user_id);
		User user = userService.getUserById(user_id);
		model.addAttribute("user", user);
//        model.addAttribute("taskList", taskList);
		return "/task/task-list";

	}
	
	@GetMapping("/list/project/{id}/user/{userId}")
	public String findAllProjectTasks(@PathVariable("id") long projectId, @PathVariable("userId") long userId,
			Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET findAllProjectTasks - called");
		User user = userService.getUserById(userId);
		Project project = projectService.getProjectById(projectId);
		model.addAttribute("user", user);
		model.addAttribute("project", project);
		return "/task/task-list";
	}
	

	// NEW TASK LIST PER ASSIGNED USER
	@GetMapping("/allTasksByUserId/{user_id}")
	public String allTasksFilteredByUserEmail(@PathVariable(value = "user_id") long user_id, Model model) {
//        List<Task> taskList = taskRepository.getAllTasksByUser(user_id);
		User user = userService.getUserById(user_id);
		model.addAttribute("user", user);
//        model.addAttribute("taskList", taskList);
		return "/task/list-by-user";

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
	}

}
