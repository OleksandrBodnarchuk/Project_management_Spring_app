package com.javawwa25.app.springboot.admin;

import java.util.List;

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

import com.javawwa25.app.springboot.project.Project;
import com.javawwa25.app.springboot.project.dto.ProjectDto;
import com.javawwa25.app.springboot.project.service.ProjectService;
import com.javawwa25.app.springboot.user.dto.GroupDto;
import com.javawwa25.app.springboot.user.dto.UserDto;
import com.javawwa25.app.springboot.user.service.UserService;

import jakarta.validation.Valid;
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
    	model.addAttribute("user", userService.getLoggedUserDto());
    	return "admin/admin_page";
    }
    
    @GetMapping("/users")
    public String adminUsers(Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUsers - called");
    	model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("userList", userService.getAllUserDtos());
    	return "admin/users";
    	
    }
    
    @GetMapping("/user/{id}")
    public String adminUserPage(@PathVariable("id") long id, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUserPage - called");
		UserDto dto = userService.getUserDtoByAccountId(id);
		model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("dto", dto);
    	return "admin/user_page";
    	
    }
    
    @PostMapping("/user/{id}")
    public String adminEditUser(@PathVariable("id") long id, @ModelAttribute("dto") @Valid UserDto dto, 
    							BindingResult result, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - POST adminEditUser - called");
    	if (result.hasErrors()) {
    		LOG.debug("[" + this.getClass().getSimpleName() + "] - POST saveUser - ERROR in FORMS");
    		model.addAttribute("user", userService.getLoggedUserDto());
    		model.addAttribute("dto", dto);
			return "admin/user_page";
		}
    	
    	userService.updateUser(dto);
		return "redirect:/admin/user/" + id + "?success";
    	
    }

    @GetMapping("/user/{id}/groups")
    public  String adminUserGroupsPage(@PathVariable("id") long id, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUserGroupsPage - called");
    	return null;
    }
    
	@GetMapping("/user/{id}/projects")
	public String adminUserProjectsPage(@PathVariable("id") long accountId, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUserProjectsPage - called");
    	UserDto dto = userService.getUserDtoByAccountId(accountId);
    	List<ProjectDto> projectList = projectService.getProjectDtosByAccountId(accountId);
    	model.addAttribute("user", userService.getLoggedUserDto());
    	model.addAttribute("dto", dto);
    	model.addAttribute("projectList", projectList);
    	return "admin/user_project";
	}
	
	@GetMapping("/user/{id}/projects/add")
	public String adminUserProjectAddPage(@PathVariable("id") long accountId, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminUserProjectAddPage - called");
    	model.addAttribute("user", userService.getLoggedUserDto());
    	model.addAttribute("dto", userService.getUserDtoByAccountId(accountId));
    	model.addAttribute("projectList", projectService.getProjectsNotPartOf(accountId));
    	return "admin/user_project_add";
	}
	
	@GetMapping("/user/{id}/assign/{projectId}")
	public String adminAssignProjectUser(@PathVariable("id") long accountId, @PathVariable("projectId") long projectId, Model model) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminAssignProjectUser - called");
    	projectService.assignProject(accountId, projectId);
    	return "redirect:/admin/user/" + accountId + "/projects?success";
	}
    
	@GetMapping("/projects")
	public String adminProjects(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminProjects - called");
		List<Project> projectList = projectService.getAllProjects();
		model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("projectList", projectList);
		return "admin/projects";
	}
	
	@GetMapping("/groups")
	public String adminGroups(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminGroups - called");
		model.addAttribute("user", userService.getLoggedUserDto());
		return "admin/group/groups";

	}
	
	@GetMapping("/groups/new")
	public String adminNewGroup(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminNewGroup - called");
		model.addAttribute("user", userService.getLoggedUserDto());
		model.addAttribute("group", new GroupDto());
		return "admin/group/group_new";
	}
	
	@PostMapping("/groups")
	public String adminGroupSave(@ModelAttribute("group") GroupDto dto, Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET adminGroupSave - called");
		LOG.debug("SAVING GROUP: " + dto.getName());
		return "redirect:/admin/groups?success";
	}
    
}
