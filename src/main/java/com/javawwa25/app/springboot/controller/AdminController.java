package com.javawwa25.app.springboot.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javawwa25.app.springboot.group.dto.GroupUsersForm;
import com.javawwa25.app.springboot.group.service.GroupService;
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
	private final GroupService groupService;
    private final UserService userService;
    private final ProjectService projectService;
    
    @GetMapping
    public String adminPage(Model model) {
    	fillLoggedUserDto(model);
    	return "admin/admin_page";
    }
    
    @GetMapping("/users")
    public String adminUsers(Model model) {
    	fillLoggedUserDto(model);
		model.addAttribute("userList", userService.getAllUserDtos());
    	return "admin/users";
    	
    }
    
    @GetMapping("/user/{id}")
    public String adminUserPage(@PathVariable("id") long id, Model model) {
		UserDto dto = userService.getUserDtoByAccountId(id);
		fillLoggedUserDto(model);
		model.addAttribute("dto", dto);
    	return "admin/user_page";
    	
    }
    
    @PostMapping("/user/{id}")
    public String adminEditUser(@PathVariable("id") long id, @ModelAttribute("dto") @Valid UserDto dto, 
    							BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		fillLoggedUserDto(model);
    		model.addAttribute("dto", dto);
			return "admin/user_page";
		}
    	
    	userService.updateUser(dto);
		return "redirect:/admin/user/" + id + "?success";
    	
    }

    @GetMapping("/user/{id}/groups")
    public  String adminUserGroupsPage(@PathVariable("id") long id, Model model) {
    	return null;
    }
    
	@GetMapping("/user/{id}/projects")
	public String adminUserProjectsPage(@PathVariable("id") long accountId, Model model) {
    	UserDto dto = userService.getUserDtoByAccountId(accountId);
    	List<ProjectDto> projectList = projectService.getProjectDtosByAccountId(accountId);
    	fillLoggedUserDto(model);
    	model.addAttribute("dto", dto);
    	model.addAttribute("projectList", projectList);
    	return "admin/user_project";
	}
	
	@GetMapping("/user/{id}/projects/add")
	public String adminUserProjectAddPage(@PathVariable("id") long accountId, Model model) {
    	fillLoggedUserDto(model);
    	model.addAttribute("dto", userService.getUserDtoByAccountId(accountId));
    	model.addAttribute("projectList", projectService.getProjectsNotPartOf(accountId));
    	return "admin/user_project_add";
	}
	
	@GetMapping("/user/{id}/assign/{projectId}")
	public String adminAssignProjectUser(@PathVariable("id") long accountId, @PathVariable("projectId") long projectId, Model model) {
    	projectService.assignProject(accountId, projectId);
    	return "redirect:/admin/user/" + accountId + "/projects?success";
	}
    
	@GetMapping("/projects")
	public String adminProjects(Model model) {
		List<Project> projectList = projectService.getAllProjects();
		fillLoggedUserDto(model);
		model.addAttribute("projectList", projectList);
		return "admin/projects";
	}
	
	@GetMapping("/groups")
	public String adminGroups(Model model) {
		fillLoggedUserDto(model);
		model.addAttribute("groupList", groupService.getSimpleGroupInfo());
		return "admin/group/groups";

	}
	
	@GetMapping("/groups/new")
	public String adminNewGroup(Model model) {
		fillLoggedUserDto(model);
		model.addAttribute("group", new GroupDto());
		return "admin/group/group_new";
	}
	
	@PostMapping("/groups")
	public String adminGroupSave(@ModelAttribute("group") @Valid GroupDto dto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			fillLoggedUserDto(model);
			model.addAttribute("dto", dto);
			return "admin/group/group_new";
		}
		groupService.save(dto);
		return "redirect:/admin/groups?success";
	}
	
	@GetMapping("/groups/{id}/edit")
	public String adminGroup(@RequestParam(value = "tab", required = false) String tab,
			@PathVariable("id") long groupId, Model model) {
		fillLoggedUserDto(model);
		model.addAttribute("group", groupService.getGroupById(groupId));
		if(StringUtils.isNotBlank(tab)) {
			return tab.equals("group") ? 
					"/admin/group/group_page" : 
					String.format("/admin/group/group_%s", tab);
		}
		return "/admin/group/group_page";
	}
	
	@GetMapping("/groups/{id}/users/new")
	public String adminGroupNewUser(@PathVariable("id") long groupId, Model model) {
		fillLoggedUserDto(model);
		GroupDto group = groupService.getGroupById(groupId);
		model.addAttribute("group", group);
		GroupUsersForm wrapperForm = new GroupUsersForm();
		wrapperForm.setUsers(userService.getAllUsersForGroup(group));
		model.addAttribute("wrapper", wrapperForm);
		return "/admin/group/users_new";
	}
	
	@PostMapping("/groups/{id}/users/new")
	public String saveGroupUsers(@PathVariable("id") long id, @ModelAttribute("wrapper") GroupUsersForm form) {
		groupService.assignUsers(id, form);
		return "redirect:/admin/groups/" + id + "/edit";
	}
	
	
	private void fillLoggedUserDto(Model model) {
		model.addAttribute("user", userService.getLoggedUserDto());
	}
	
}
