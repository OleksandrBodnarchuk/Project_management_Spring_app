package com.javawwa25.app.springboot.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;

@Controller
public class UserController {

	private final static Logger LOG = LoggerFactory.getLogger(UserController.class);
	
    private final UserService userService;
    private final ProjectService projectService;
    
    public UserController(UserService userService, ProjectService projectService) {
		this.userService = userService;
		this.projectService = projectService;
	}

    @GetMapping(path = "/users")
    public String userLoggedIn() {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET userLoggedIn - called");
    	userService.userLogged();
		return "redirect:/users/" + getUserId();
    }
    
    @GetMapping("/users/{userId}")
    public String showUserPage(@PathVariable(value = "userId") long userId, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showUserPage - called");
    	projectService.fillUserProjects(userId, model);
        return "user/user-page";
    }

    @GetMapping("/users/new")
    public String showNewUserForm(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showNewUserForm - called");
        User user = new User();
        model.addAttribute("user", user);
        return "user/new_user";
    }
    
    private long getUserId() {
    	return userService.getLoggedUserId();
    }


}
