package com.javawwa25.app.springboot.controllers;


import com.javawwa25.app.springboot.models.Progress;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.TaskRepository;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.javawwa25.app.springboot.models.Progress.*;


@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    private UserService userService;

    // adding Task to specific project
    @GetMapping("/showNewTaskForm/{project_id}")
    public String showNewTaskForm(@PathVariable(value = "project_id") long project_id, Model model) {
        Task task = new Task();
        Project project = projectService.getProjectById(project_id);
        model.addAttribute("project", project);
        model.addAttribute("task", task);
        return "/task/new_task";
    }

    @PostMapping("/saveNewTask/{project_id}")
    public String saveTask(@PathVariable(value = "project_id") long project_id, @ModelAttribute("task") Task task) {
        task.setTask_progress(TODO);
        // Assign current project to task
        task.setProject(projectService.getProjectById(project_id));
        taskService.saveTask(task);
        return "redirect:/user";
    }

    @GetMapping("/showTaskInfo/{task_id}")
    public String showTaskInfo(@PathVariable(value = "task_id") long task_id, Model model) {
        Task task = taskService.getTaskById(task_id);
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        model.addAttribute("task", task);
        return "/task/task-info";
    }

    @PostMapping("/saveCurrentTask")
    public String saveCurrentTask( @ModelAttribute("task") Task task) {
        taskService.saveTask(task);
        return "redirect:/user";
    }

    @GetMapping("/deleteTask/{task_id}")
    public String deleteTask(@PathVariable(value = "task_id") long task_id) {
        // call delete project method
        this.taskService.deleteTaskById(task_id);
        return "redirect:/user";
    }

    @GetMapping("/updateTaskInBoard/{task_id}")
    public String updateTaskInBoard(@PathVariable(value = "task_id") long task_id, Model model) {
        Task task = taskService.getTaskById(task_id);
        task.setProject(projectService.getProjectById(task.getProject().getProject_id()));
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        model.addAttribute("task", task);
        return "/task/update_task";
    }

    @GetMapping("/moveTaskToNextStep/{task_id}")
    public String moveTaskToNextStep(@PathVariable(value = "task_id") long task_id) {
        Task task = taskService.getTaskById(task_id);
        switch (task.getTask_progress()) {
            case TODO:
                task.setTask_progress(IN_PROGRESS);
                break;
            case IN_PROGRESS:
                task.setTask_progress(QA);
                break;
            case QA:
                task.setTask_progress(DONE);
                break;
        }
        taskService.saveTask(task);
        return "redirect:/";
    }

    @GetMapping("/allTasksByUser/{user_id}")
    public String allTasksByUser(@PathVariable(value = "user_id")long user_id, Model model){
        List<Task> taskList = taskRepository.getAllTasksByUserId(user_id);
        model.addAttribute("taskList", taskList);
        return "/task/task-list";

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

}
