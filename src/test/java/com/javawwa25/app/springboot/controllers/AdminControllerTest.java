package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;

import com.javawwa25.app.springboot.controller.AdminController;
import com.javawwa25.app.springboot.group.service.GroupService;
import com.javawwa25.app.springboot.project.service.ProjectService;
import com.javawwa25.app.springboot.security.validators.GroupNameValidator;
import com.javawwa25.app.springboot.user.dto.GroupDto;
import com.javawwa25.app.springboot.user.dto.UserDto;
import com.javawwa25.app.springboot.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

	private static final String PATH = "/admin";

	@Mock
	private Model model;
	
	@Mock
	private UserService userService;
	
	@Mock
	private ProjectService projectService;
	
	@Mock
	private GroupService groupService;
	
	@InjectMocks
	private AdminController underTest;

	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(underTest).build();

	}
	
	@Test
	void testAdminPage() throws Exception {
		String expected = "admin/admin_page";
		underTest.adminPage(model);
		verify(model, times(1)).addAttribute(any(), any());
		mvc.perform(get(PATH))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
	}

	@Test
	void testAdminUsers() throws Exception {
		String expected = "admin/users";
		underTest.adminUsers(model);
		verify(model, times(2)).addAttribute(any(), any());
		mvc.perform(get(PATH + "/users"))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
	}

	@Test
	void testAdminProjects() throws Exception {
		String expected = "admin/projects";
		underTest.adminProjects(model);
		verify(model, times(2)).addAttribute(any(), any());
		mvc.perform(get(PATH + "/projects"))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
	}
	
	@Test
	void testAdminUserPage() throws Exception {
		String expected = "admin/user_page";
		String actual = underTest.adminUserPage(1l, model);
		given(userService.getUserDtoByAccountId(anyLong())).willReturn(UserDto.builder().build());
		verify(model, times(2)).addAttribute(any(), any());
		mvc.perform(get(PATH + "/user/{id}", 1l))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
		
		assertEquals(expected, actual);
	}
	
	@Test
    public void testAdminEditUser() throws Exception {
		String expected = "redirect:/admin/user/1?success";
		UserDto dto = UserDto.builder().build();
		dto.setFirstName("firstName");
		dto.setLastName("lastName");
		dto.setEmail("email@email.com");
		dto.setPassword("12345678");
		mvc.perform(post(PATH + "/user/{id}", 1l).flashAttr("dto", dto))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(expected));
    }
	
	@Test
    public void testAdminEditUser_hasErrors() throws Exception {
		String expected = "admin/user_page";
		UserDto dto = UserDto.builder().build();
		dto.setLastName("lastName");
		dto.setEmail("email@email.com");
		dto.setPassword("12345678");
		
		BindingResult result = mock(BindException.class);
		result.addError(new ObjectError("TEMP", "TEMP"));
		given(result.hasErrors()).willReturn(true);

		String actual = underTest.adminEditUser(1l, dto, result, model);
		verify(model, times(2)).addAttribute(any(), any());
		verify(userService, never()).update(any());

		assertEquals(expected, actual);
    }
	
	@Test
	void testAdminUserProjectsPage() throws Exception {
		String expected = "admin/user_project";
		given(projectService.getProjectDtosByAccountId(anyLong())).willReturn(List.of());
		given(userService.getUserDtoByAccountId(anyLong())).willReturn(UserDto.builder().build());
		given(userService.getLoggedUserDto()).willReturn(UserDto.builder().build());
		underTest.adminUserProjectsPage(1l, model);
		verify(model, times(3)).addAttribute(any(), any());
		
		mvc.perform(get(PATH + "/user/{id}/projects", 1)).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name(expected));
	}
	
	@Test
	void testAdminUserProjectAddPage() throws Exception {
		String expected = "admin/user_project_add";
		given(projectService.getProjectsNotPartOf(anyLong())).willReturn(List.of());
		given(userService.getUserDtoByAccountId(anyLong())).willReturn(UserDto.builder().build());
		given(userService.getLoggedUserDto()).willReturn(UserDto.builder().build());
		underTest.adminUserProjectsPage(1l, model);
		verify(model, times(3)).addAttribute(any(), any());
		
		mvc.perform(get(PATH + "/user/{id}/projects/add", 1)).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name(expected));
	}
	
	@Test
	public void testAdminAssignProjectUser() throws Exception {
    	String expected = "redirect:/admin/user/1/projects?success";
    	underTest.adminAssignProjectUser(1, 1, model);
    	verify(projectService, times(1)).assignProject(anyLong(), anyLong());
		mvc.perform(get(PATH + "/user/{id}/assign/{projectId}", 1,1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name(expected));
	}
	
	@Test
	public void testAdminGroups() {
		String expected= "admin/group/groups";
		String actual = underTest.adminGroups(model);
		verify(model, times(2)).addAttribute(any(), any());
		verify(userService, times(1)).getLoggedUserDto();
		verify(groupService, times(1)).getSimpleGroupInfo();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAdminGroups_Request() throws Exception {
		String expected= "admin/group/groups";
		mvc.perform(get(PATH + "/groups"))
				.andExpect(status().isOk())
		.andExpect(view().name(expected));
	}

	@Test
	public void testAdminNewGroup() {
		String expected=  "admin/group/group_new";
		String actual = underTest.adminNewGroup(model);
		verify(model, times(2)).addAttribute(any(), any());
		verify(userService, times(1)).getLoggedUserDto();
		assertEquals(expected, actual);
	}

	@Test
	public void testAdminNewGroup_Request() throws Exception {
		String expected=  "admin/group/group_new";
		mvc.perform(get(PATH + "/groups/new"))
		.andExpect(status().isOk())
		.andExpect(view().name(expected));
	}

	@Test
	public void testAdminGroupSave() {
		String expected= "redirect:/admin/groups?success";
		GroupDto dto = new GroupDto();
		dto.setName("GROUP_NAME");
		BindingResult result = mock(BindException.class);
		String actual = underTest.adminGroupSave(dto, result, model);
		verify(groupService, times(1)).save(any());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAdminGroupSave_hasError() {
		String expected= "admin/group/group_new";
		GroupDto dto = new GroupDto();
		dto.setName("GROUP_NAME");
		BindingResult result = mock(BindException.class);
		result.addError(new ObjectError("TEMP", "TEMP"));
		given(result.hasErrors()).willReturn(true);

		String actual = underTest.adminGroupSave(dto, result, model);
		verify(model, times(2)).addAttribute(any(), any());
		verify(groupService, never()).save(any());
		assertEquals(expected, actual);
	}

	@Test
	public void testAdminGroupSave_Request() throws Exception {
		String expected= "redirect:/admin/groups?success";
		GroupDto dto = new GroupDto();
		dto.setName("GROUP_NAME");
		GroupNameValidator mock = mock(GroupNameValidator.class);
		given(mock.isValid(any(), any())).willReturn(true);
		mvc.perform(post(PATH + "/groups").flashAttr("dto", dto))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(expected));
	}
	
	@GetMapping("/groups/{id}")
	public void adminGroup() {
		String expected = "/admin/group_page"; 
		String actual = underTest.adminGroup(null, 1, model);
		assertEquals(expected, actual);
	}
	
	@Test
	public void adminGroup_Request() throws Exception {
		String expected = "/admin/group/group_page"; 
		mvc.perform(get(PATH + "/groups/{id}/edit", 1))
		.andExpect(status().isOk())
		.andExpect(view().name(expected));
	}

}
