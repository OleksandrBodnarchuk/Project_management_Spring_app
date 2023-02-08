package com.javawwa25.app.springboot.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.repositories.TaskRepository;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;


@Controller
@RequestMapping("/project")
public class ProjectController {

	private final  ProjectService projectService;
	private final  TaskService taskService;
	private final  UserService userService;
	private final  TaskRepository taskRepository;

    public ProjectController(ProjectService projectService, TaskService taskService, UserService userService,
			TaskRepository taskRepository) {
		this.projectService = projectService;
		this.taskService = taskService;
		this.userService = userService;
		this.taskRepository = taskRepository;
	}

	@GetMapping("/new/user/{id}")
    public String showNewProjectForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("project", new Project());
        return "project/new_project";
    }

    @PostMapping("/saveProject")
    public String saveProject(String userName, @ModelAttribute("project") Project project) {
        // Assigning current user to new project
        project.setStartDate(LocalDate.now());
        project.getUsers().add(userService.findByEmail(userName));
        projectService.save(project);
        return "redirect:/user";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get project from the service
        Project project = projectService.getProjectById(id);
        // set project as a model attribute to pre-populate the form
        model.addAttribute("project", project);
        return "project/update_project";
    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable(value = "id") long id) {

        // call delete project method
        this.projectService.deleteProjectById(id);
        return "redirect:/";   // CHECK REDIRECT !!!!!!!!!!!!
    }

    @GetMapping("/redirect/{id}")
    public String redirectToTask(@PathVariable(value="id")long id){
        return "redirect:/task/showNewTaskForm/"+id;
    }

    @GetMapping("/board/{project_id}")
    public String getBoardByProjectId(@PathVariable(value = "project_id") long project_id,
                           Model model) {
//        List<Task> toDoTaskList = taskRepository.getAllTasksOnTODOStep(project_id);
//        List<Task> qaTaskList = taskRepository.getAllTasksOnQAStep(project_id);
//        List<Task> inProgressTaskList = taskRepository.getAllTasksOnInProgressStep(project_id);
//        List<Task> doneTaskList = taskRepository.getAllTasksOnDONEStep(project_id);
//        model.addAttribute("project", projectService.getProjectById(project_id));
//        model.addAttribute("toDoTaskList", toDoTaskList);
//        model.addAttribute("qaTaskList", qaTaskList);
//        model.addAttribute("inProgressTaskList", inProgressTaskList);
//        model.addAttribute("doneTaskList", doneTaskList);
        return "/board/board1";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

}
