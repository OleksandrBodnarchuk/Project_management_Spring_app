package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.repositories.UserRepository;
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
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/showNewProjectForm")
    public String showNewProjectForm(@CurrentSecurityContext(expression="authentication.name") String userName, Model model) {
        // create model attribute to bind form data
        User user = userRepository.findByEmail(userName);
        Project project = new Project();
        model.addAttribute("user", user);
        model.addAttribute("project", project);
        return "project/new_project";
    }


    @PostMapping("/saveProject")
    public String saveProject(@CurrentSecurityContext(expression="authentication.name") String userName, @ModelAttribute("project") Project project) {
        // Assigning current user to new project
        project.setUser(userRepository.findByEmail(userName));
        projectService.saveProject(project);
        return "redirect:/user";
    }


    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {

        // get project from the service
        Project project = projectService.getProjectById(id);
        // set project as a model attribute to pre-populate the form
        model.addAttribute("project", project);
        return "project/update_project";
    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable (value = "id") long id) {

        // call delete project method
        this.projectService.deleteProjectById(id);
        return "redirect:/project/project-list";   // CHECK REDIRECT !!!!!!!!!!!!
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(       Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

}
