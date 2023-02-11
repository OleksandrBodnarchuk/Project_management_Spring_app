package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserDto;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private Model model;
	@Mock
	private UserService userService;
	@Mock
	private ProjectService projectService;

	@InjectMocks
	private UserController underTest;
	
	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(underTest).build();

	}

	@Test
	void test_showUserPage() throws Exception {
		String expectedTemplate = "user/user-page";
		long userId = 1l;
		
		doNothing().when(projectService)
		.fillUserProjects(userId, model);
		
		underTest.showUserPage(userId, model);
		verify(projectService,times(1))
			.fillUserProjects(userId, model);
		
		mvc.perform(get("/users/{userId}", userId))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name(expectedTemplate));
	}
	
	@Test
	void test_showNewUserForm() throws Exception {
		String expectedTemplate = "user/new_user";
		underTest.showNewUserForm(model);
		verify(model,times(1)).addAttribute(eq("user"), any());
		mvc.perform(get("/users/new"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name(expectedTemplate));
	}
	
	@Test
	void test_userLoggedIn() throws Exception {
		long id = 1L;
		String expectedTemplate = "redirect:/users/" + id;
		when(userService.getLoggedUserId()).thenReturn(id);
		mvc.perform(get("/users"))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(expectedTemplate));
	}
	
	@Test
    public void userEditPage() throws Exception {
		String expectedTemplate ="user/edit-profile";
		doNothing().when(userService).fillUserDtoModel(anyLong(), any());  
    	mvc.perform(get("/users/{userId}/settings", 1))
		.andDo(print())
		.andExpect(status().isOk()).andExpect(view().name(expectedTemplate));
    }
    
	@Test
    public void userSecurity() throws Exception {
    	String expectedTemplate = "user/security-page";
		doNothing().when(userService).fillUserDtoModel(anyLong(), any());  
		
    	mvc.perform(get("/users/{id}/security", 1))
		.andDo(print())
		.andExpect(status().isOk()).andExpect(view().name(expectedTemplate));
    }
    
	
	@Test
    public void updateUser_hasErrors() {
		BindingResult result = mock(BindException.class);
		result.addError(new ObjectError("TEMP","TEMP"));
		given(result.hasErrors()).willReturn(true);
		
		String expected = "user/edit-profile";
		String actual = underTest.updateUser(new UserDto(), result, model);
		verify(model, times(1)).addAttribute(anyString(), any());
		verify(userService, never()).update(any());
		
		assertEquals(expected, actual);
    }

}
