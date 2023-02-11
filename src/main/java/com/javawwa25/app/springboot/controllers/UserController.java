package com.javawwa25.app.springboot.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserDto;

import jakarta.validation.Valid;

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
    
    @GetMapping("/users/{userId}/settings")
    public String userEditPage(@PathVariable("userId") long userId, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET userEditPage - called");
    	userService.fillUserDtoModel(userId, model);
        return "user/edit-profile";
    }
    
    @GetMapping("/users/{id}/security")
    public String userSecurity(@PathVariable("id") long userId, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET userSecurity - called");
    	userService.fillUserDtoModel(userId, model);
        return "user/security-page";
    }
    
    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("dto") @Valid UserDto dto, BindingResult result, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - POST updateUser - called");
    	if (result.hasErrors()) {
			model.addAttribute("dto", dto);
			return "user/edit-profile";
		}
		userService.update(dto);
		model.addAttribute("dto", dto);
		return "redirect:/users/" + dto.getAccountId() + "/settings?success";
    }
    
    private long getUserId() {
    	return userService.getLoggedUserId();
    }


}
