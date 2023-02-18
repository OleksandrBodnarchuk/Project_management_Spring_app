package com.javawwa25.app.springboot.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@Secured("ADMIN")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    
    @GetMapping
    public String adminPage(Model model) {
    	userService.fillUserDtoModel(model);
    	return "admin/admin_page";
    }
    
    @GetMapping("/users")
    public String adminUsers(Model model) {
    	userService.fillUserDtoModel(model);
    	userService.fillAllUsersForAdmin(model);
    	return "admin/users_page";
    	
    }
    
    @GetMapping("/{userId}/projects")
    public String adminProjects(@PathVariable("userId") long userId, Model model) {
    /* TODO:
     * add user
     * add projects
     * */ 
    	return "admin/users_page";
    	
    }
    
}
