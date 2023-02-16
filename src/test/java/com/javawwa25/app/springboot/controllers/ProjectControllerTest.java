package com.javawwa25.app.springboot.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

	private static final String PROJECTS_ENDPOINT = "/users/{userId}/projects";
	
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
		underTest.projectList(anyLong(), model);
		verify(model, times(2)).addAttribute(anyString(), any());
		verify(projectService, times(1)).getAllProjectsByUserId(anyLong());
		verify(userService, times(1)).getUserById(anyLong());
		mvc.perform(get(PROJECTS_ENDPOINT, 1))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name(expectedTemplate));
	}

	@Test
	void testShowNewProjectForm() throws Exception {
		String expectedTemplate = "project/new_project";
		underTest.showNewProjectForm(anyLong(), model);
		verify(model, times(2)).addAttribute(anyString(), any());
		mvc.perform(get(PROJECTS_ENDPOINT+"/add", 1))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name(expectedTemplate));
	}

	@Test
	void testRedirectToProjectTasks() throws Exception {
		int projectId = 1;
		int userId = 1;
		String expectedTemplate = "redirect:" + PROJECTS_ENDPOINT + "/" + projectId + "/tasks";
		underTest.showNewProjectForm(anyLong(), model);
		verify(model, times(2)).addAttribute(anyString(), any());
		mvc.perform(get(PROJECTS_ENDPOINT+"/{projectId}/tasks", userId, projectId))
		.andDo(print())
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(expectedTemplate));
	}

	@Test
	void testSaveProject() throws Exception {
		long userId = 1;
		Project pythonProject = Project.builder().startDate(LocalDate.now()).name("Python").info("Description").build();
		String expectedTemplate = "redirect:" + PROJECTS_ENDPOINT;
		
		underTest.saveProject(userId, pythonProject);
		verify(projectService, times(1)).save(any());
		
		mvc.perform(post(PROJECTS_ENDPOINT + "/save", userId)
				.flashAttr("project", pythonProject))
		.andDo(print())
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(expectedTemplate));
	}

	@Test
	void testShowFormForUpdate() throws Exception {
		String expectedTemplate = "project/update_project";
		long userId = 1;
		long projectId = 1;
		
		underTest.showFormForUpdate(projectId, model);
		verify(projectService, times(1)).getProjectById(projectId);
		
		mvc.perform(get(PROJECTS_ENDPOINT + "/{projectId}/update", userId, projectId))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name(expectedTemplate));
	}

}
