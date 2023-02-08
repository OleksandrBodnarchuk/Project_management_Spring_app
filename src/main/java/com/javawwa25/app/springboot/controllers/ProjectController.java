package com.javawwa25.app.springboot.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;


@Controller
@RequestMapping("/projects")
public class ProjectController {
	private final static Logger LOG = LoggerFactory.getLogger(ProjectController.class);
	
	private final  ProjectService projectService;
	private final  UserService userService;

	public ProjectController(ProjectService projectService, UserService userService) {
		this.projectService = projectService;
		this.userService = userService;
	}


	@GetMapping
    public String projectListNoUser(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET projectListNoUser - called");
        model.addAttribute("projects", projectService.getAllProjects());
        return "project/project_list_no_user";
    }
	
	@GetMapping("/user/{id}")
    public String projectList(@PathVariable("id") long id, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET projectList - called");
		model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("projects", projectService.getAllProjects());
        return "project/project_list";
    }

	@GetMapping("/new/user/{id}")
    public String showNewProjectForm(@PathVariable("id") long id, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showNewProjectForm - called");
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("project", new Project());
        return "project/new_project";
    }
	
	@GetMapping("/{id}/tasks/user/{userId}")
	public String redirectToProjectTasks(@PathVariable("id") long id, @PathVariable("userId") long userId) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET redirectToProjectTasks - called");
		return "redirect:/tasks/list/project/" + id + "/user/" + userId;
	}

    @PostMapping("/saveProject")
    public String saveProject(String userName, @ModelAttribute("project") Project project) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET saveProject - called");
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
