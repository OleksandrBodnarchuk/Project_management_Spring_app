package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

	@Mock
	private Model model;
	@Mock
	private UserService userService;
	@Mock
	private ProjectService projectService;

	@InjectMocks
	private ProjectController underTest;

	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(underTest).build();

	}

	@Test
	void testProjectList() throws Exception {
		String expectedTemplate = "project/project_list";
		underTest.projectList(anyLong(),model);
		verify(model,times(2)).addAttribute(anyString(), any());
		verify(projectService,times(1)).getAllProjects();
		verify(userService,times(1)).getUserById(anyLong());
		mvc.perform(get("/projects/user/{id}",1))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name(expectedTemplate));
	}

}
