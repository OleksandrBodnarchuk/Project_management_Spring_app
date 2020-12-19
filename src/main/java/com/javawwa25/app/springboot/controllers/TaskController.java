package com.javawwa25.app.springboot.controllers;


import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    // display list of employees
    @GetMapping("/tasks")
    public String viewHomePage(Model model) {
        return findPaginated(1, "projectName", "asc", model);
    }

    @GetMapping("/showNewTaskForm/task")
    public String showNewTaskForm(Model model) {
        // create model attribute to bind form data
        Task task = new Task();

        model.addAttribute("task", task);
        return "new_task";
    }

    @PostMapping("/saveTask/task")
    public String saveTask(@ModelAttribute("task") Task task) {
       taskService.saveTask(task);
        return "redirect:/task";   // CHECK REDIRECT !!!!!!!!!!!!
    }


    @GetMapping("/showFormForUpdate/task/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {

        // get project from the service
        Task task = taskService.getTaskById(id);

        // set project as a model attribute to pre-populate the form
        model.addAttribute("task", task);
        return "update_task";
    }

    @GetMapping("/deleteTask/task/{id}")
    public String deleteTask(@PathVariable (value = "id") long id) {

        // call delete project method
        this.taskService.deleteTaskById(id);
        return "redirect:/task";   // CHECK REDIRECT !!!!!!!!!!!!
    }


    @GetMapping("/task/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Task> page = taskService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Task> taskList = taskService.getAllTasks();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("taskList", taskList);
        return "task-list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(       Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

}
