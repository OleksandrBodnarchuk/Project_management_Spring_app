package com.javawwa25.app.springboot.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private final static Logger LOG = LoggerFactory.getLogger(UserController.class);
	
    private final UserService userService;
    private final ProjectService projectService;
    
    public UserController(UserService userService, ProjectService projectService) {
		this.userService = userService;
		this.projectService = projectService;
	}

    // MAIN PAGE FOR USER
    @GetMapping("/{user_id}")
    public String showUserPage(@PathVariable(value = "user_id") long user_id, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showUserPage - called");
        User user = userService.getUserById(user_id);
        List<Project> projectList = projectService.getAllProjectsByUserId(user_id);
        model.addAttribute("user", user);
        model.addAttribute("projectList", projectList);
        return "user/user-page";
    }

    // for admin only
    @GetMapping("/new")
    public String showNewUserForm(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showNewUserForm - called");
        User user = new User();
        model.addAttribute("user", user);
        return "user/new_user";
    }


}
