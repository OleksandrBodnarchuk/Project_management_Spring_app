package com.javawwa25.app.springboot.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	private final static Logger LOG = LoggerFactory.getLogger(UserController.class);
	
    private final UserService userService;
    private final TaskService taskService;
    
    public UserController(UserService userService, TaskService taskService) {
		this.userService = userService;
		this.taskService = taskService;
	}

    @GetMapping
    public String userPage(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET userLoggedIn - called");
    	userService.userLogged();
    	taskService.fillUserPageDtoModel(model);
    	return "user/user-page";
    }
    
    @GetMapping("/new")
    public String showNewUserForm(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET showNewUserForm - called");
        User user = new User();
        model.addAttribute("user", user);
        return "user/new_user";
    }
    
    @GetMapping("/settings")
    public String userEditPage(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET userEditPage - called");
    	userService.fillUserDtoModel(model);
        return "user/edit-profile";
    }
    
    @GetMapping("/security")
    public String userSecurity(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET userSecurity - called");
    	userService.fillUserDtoModel(model);
        return "user/security-page";
    }
    
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("dto") @Valid UserDto dto, BindingResult result, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - POST updateUser - called");
    	if (result.hasErrors()) {
			model.addAttribute("dto", dto);
			return "user/edit-profile";
		}
		userService.update(dto);
		model.addAttribute("dto", dto);
		return "redirect:/user/settings?success";
    }
}
