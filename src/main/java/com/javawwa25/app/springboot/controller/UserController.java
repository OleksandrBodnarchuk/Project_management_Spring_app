package com.javawwa25.app.springboot.controller;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.task.service.TaskService;
import com.javawwa25.app.springboot.user.dto.UserDto;
import com.javawwa25.app.springboot.user.dto.UserRegistrationDto;
import com.javawwa25.app.springboot.user.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    
    public UserController(UserService userService, TaskService taskService) {
		this.userService = userService;
		this.taskService = taskService;
	}

    @GetMapping
    public String userPage(Model model) {
       	userService.userLogged();
		model.addAttribute("createdTasks", taskService.getCreatedTasksForUser());
		model.addAttribute("assignedTasks", taskService.getAssignedTasksForUser());
        model.addAttribute("user", userService.getLoggedUserDto());
    	return "user/user-page";
    }
    
    @Secured({"ADMIN"})
    @GetMapping("/new")
    public String showNewUserForm(Model model) {
    	model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("dto", new UserRegistrationDto());
        return "user/new_user";
    }
    
	@Secured({"ADMIN"})
	@PostMapping("/save")
    public String saveUser(@ModelAttribute("dto") @Valid UserRegistrationDto dto, BindingResult result, Model model) {
    	if (result.hasErrors()) {

    		model.addAttribute("user", userService.getLoggedUserDto());
    		model.addAttribute("dto", dto);
			return "user/new_user";
		}
        userService.saveRegister(dto);
		return "redirect:/admin/users?success";
    }
    
    @GetMapping("/settings")
    public String userEditPage(Model model) {
    	model.addAttribute("user", userService.getLoggedUserDto());
        return "user/edit-profile";
    }
    
    @GetMapping("/security")
    public String userSecurity(Model model) {
    	model.addAttribute("user", userService.getLoggedUserDto());
        return "user/security-page";
    }
    
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("dto") @Valid UserDto dto, BindingResult result, Model model) {
    	if (result.hasErrors()) {
			model.addAttribute("dto", dto);
			return "user/edit-profile";
		}
		userService.update(dto);
		model.addAttribute("dto", dto);
		return "redirect:/user/settings?success";
    }
}
