package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.ProjectDto;
import com.javawwa25.app.springboot.web.dto.UserDto;

@ExtendWith(MockitoExtension.class)
class IssueControllerTest {
	private final static String TASK_ENDPOINT = "/tasks/projects/{projectId}";
	
	@Mock
	private Model model;
	@Mock
	private TaskService taskService;
	@Mock
	private ProjectService projectService;
	@Mock
	private UserService userService;

	@InjectMocks
	private IssueController underTest;

	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(underTest).build();

	}

	@Test
	void testCreateTask() throws Exception {
		String expected = "/task/new_task";
		given(userService.getLoggedUserDto()).willReturn(UserDto.builder().build());
		mvc.perform(get(TASK_ENDPOINT + "/new", 1))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
		
		String actual = underTest.createTask(1l, model);
		verify(model, times(3)).addAttribute(any(), any());
		assertEquals(expected, actual);
	}

	@Test
	void testSaveTask() {
		String expected = "redirect:/users/{id}";
		fail("Not yet implemented");
	}

	@Test
	void testShowTaskInfo() {
		String expected = "/task/task-info";
		fail("Not yet implemented");
	}

	@Test
	void testUpdateTaskInBoard() {
		String expected = "/task/update_task";
		fail("Not yet implemented");
	}

	@Test
	void testMoveTaskToNextStep() {
		String expected;
		fail("Not yet implemented");
	}

	@Test
	void testMoveTaskToPreviousStep() {
		long userId = 1l;
		long projectId = 1l;
		String expected = "redirect:/users/" + userId + "/projects/" + projectId + "/tasks";
		;
		fail("Not yet implemented");
	}

	@Test
	void testProjectAllTasks() throws Exception {
		String expected = "/task/task-list";
		given(userService.getLoggedUserDto()).willReturn(UserDto.builder().build());
		given(projectService.getProjectDtoById(anyLong())).willReturn(ProjectDto.builder().build());
		mvc.perform(get(TASK_ENDPOINT, 1))
		.andExpect(status().isOk())
		.andExpect(view().name(expected));
		
		String actual = underTest.projectAllTasks(1l, model);
		verify(model, times(2)).addAttribute(any(), any());
		
		assertEquals(expected, actual);
	}

}
