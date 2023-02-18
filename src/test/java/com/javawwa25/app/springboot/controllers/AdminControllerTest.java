package com.javawwa25.app.springboot.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.javawwa25.app.springboot.services.UserService;
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

	private static final String PATH = "/admin";

	@Mock
	private Model model;
	
	@Mock
	private UserService userService;
	
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
		doNothing().when(userService).fillUserDtoModel(any());
		mvc.perform(get(PATH))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
	}

	@Test
	void testAdminUsers() throws Exception {
		String expected = "admin/users";
		doNothing().when(userService).fillUserDtoModel(any());
		doNothing().when(userService).fillAllUsersForAdmin(any());
		mvc.perform(get(PATH + "/users"))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
	}

	@Test
	void testAdminProjects() throws Exception {
		String expected = "admin/projects";
		mvc.perform(get(PATH + "/projects"))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
	}

}
