package com.javawwa25.app.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@Secured("ADMIN")
@RequiredArgsConstructor
public class AdminController {
	private final static Logger LOG = LoggerFactory.getLogger(AdminController.class);
	
    private final UserService userService;
    private final ProjectService projectService;
    
    @GetMapping
    public String adminPage(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminPage - called");
    	userService.fillUserDtoModel(model);
    	return "admin/admin_page";
    }
    
    @GetMapping("/users")
    public String adminUsers(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUsers - called");
    	userService.fillAllUsersForAdmin(model);
    	return "admin/users";
    	
    }
    
    @GetMapping("/user/{id}")
    public String adminUserPage(@PathVariable("id") long id, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUserPage - called");
    	userService.fillAdminUserDtoModel(id, model);
    	return "admin/user_page";
    	
    }
    
    @PostMapping("/user/{id}")
    public String adminEditUser(@ModelAttribute("dto") UserRegistrationDto dto, 
    							@PathVariable("id") long id, BindingResult result, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - POST adminEditUser - called");
    	if (result.hasErrors()) {
    		LOG.debug("[" + this.getClass().getSimpleName() + "] - POST saveUser - ERROR in FORMS");
    		userService.fillUserDtoRegistrationModel(dto, model);
			return "user/new_user";
		}
    	
    	userService.updateUser(dto);
		return "redirect:/admin/user/" + dto.getAccountId() + "?success";
    	
    }

    @GetMapping("/user/{id}/groups")
    public  String adminUserGroupsPage(@PathVariable("id") long id, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUserGroupsPage - called");
    	userService.fillAdminUserDtoModel(id, model);
    	return null;
    }
	@GetMapping("/user/{id}/projects")
	public String adminUserProjectsPage(@PathVariable("id") long id, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUserProjectsPage - called");
    	userService.fillAdminUserDtoModel(id, model);
    	return null;
	}
    
	@GetMapping("/projects")
	public String adminProjects(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminProjects - called");
		projectService.fillAllProjectsForAdmin(model);
		return "admin/projects";

	}
    
}
