package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Set;

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

import com.javawwa25.app.springboot.security.validators.EmailValidator;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserDto;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private Model model;
	@Mock
	private UserService userService;
	@Mock
	private ProjectService projectService;
	@Mock
	private TaskService taskService;
	@Mock
	private BindingResult result;
	@Mock 
	private EmailValidator emailValidator;

	@InjectMocks
	private UserController underTest;

	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(underTest).build();

	}

	@Test
	void testUserPage() throws Exception {
		String expectedTemplate = "user/user-page";
		given(taskService.getCreatedTasksForUser()).willReturn(Set.of());
		given(taskService.getAssignedTasksForUser()).willReturn(Set.of());
		given(userService.getLoggedUserDto()).willReturn(new UserDto());	
		underTest.userPage(model);

		mvc.perform(get("/user")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name(expectedTemplate));
	}

	@Test
	void test_showNewUserForm() throws Exception {
		String expectedTemplate = "user/new_user";
		underTest.showNewUserForm(model);
		verify(model, times(2)).addAttribute(any(), any());
		mvc.perform(get("/user/new")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name(expectedTemplate));
	}

	@Test
	public void userEditPage() throws Exception {
		String expectedTemplate = "user/edit-profile";
		mvc.perform(get("/user/settings"))
			.andDo(print())
			.andExpect(status().isOk())
				.andExpect(view().name(expectedTemplate));
	}

	@Test
	public void userSecurity() throws Exception {
		String expectedTemplate = "user/security-page";
		mvc.perform(get("/user/security"))
			.andDo(print()).andExpect(status().isOk())
			.andExpect(view().name(expectedTemplate));
	}

	@Test
	public void updateUser_hasErrors() {
		BindingResult result = mock(BindException.class);
		result.addError(new ObjectError("TEMP", "TEMP"));
		given(result.hasErrors()).willReturn(true);

		String expected = "user/edit-profile";
		String actual = underTest.updateUser(new UserDto(), result, model);
		verify(model, times(1)).addAttribute(anyString(), any());
		verify(userService, never()).update(any());

		assertEquals(expected, actual);
	}
	
	@Test
	public void testSaveUser_hasErrors() {
		BindingResult result = mock(BindException.class);
		result.addError(new ObjectError("TEMP", "TEMP"));
		given(result.hasErrors()).willReturn(true);

		String expected = "user/new_user";
		String actual = underTest.saveUser(new UserRegistrationDto(), result, model);
		verify(model, times(2)).addAttribute(any(), any());
		verify(userService, never()).saveRegister(any());

		assertEquals(expected, actual);
	}

}
