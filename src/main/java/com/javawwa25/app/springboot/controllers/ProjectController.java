package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.models.Progress;
import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.repositories.ProjectRepository;
import com.javawwa25.app.springboot.repositories.TaskRepository;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;


    @GetMapping("/showNewProjectForm")
    public String showNewProjectForm(@CurrentSecurityContext(expression = "authentication.name") String userName, Model model) {
        User user = userRepository.findByEmail(userName);
        Project project = new Project();
        model.addAttribute("user", user);
        model.addAttribute("project", project);
        return "project/new_project";
    }


    @PostMapping("/saveProject")
    public String saveProject(@CurrentSecurityContext(expression = "authentication.name") String userName, @ModelAttribute("project") Project project) {
        // Assigning current user to new project
        Date date = new Date();
        project.setProject_startDate(date);
        project.setUser(userRepository.findByEmail(userName));
        projectService.saveProject(project);
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
        List<Task> toDoTaskList = taskRepository.getAllTasksOnTODOStep(project_id);
        List<Task> qaTaskList = taskRepository.getAllTasksOnQAStep(project_id);
        List<Task> inProgressTaskList = taskRepository.getAllTasksOnInProgressStep(project_id);
        List<Task> doneTaskList = taskRepository.getAllTasksOnDONEStep(project_id);
        model.addAttribute("project", projectService.getProjectById(project_id));
        model.addAttribute("toDoTaskList", toDoTaskList);
        model.addAttribute("qaTaskList", qaTaskList);
        model.addAttribute("inProgressTaskList", inProgressTaskList);
        model.addAttribute("doneTaskList", doneTaskList);
        return "/board/board1";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

}
