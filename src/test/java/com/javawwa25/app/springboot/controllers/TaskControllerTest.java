package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.javawwa25.app.springboot.project.service.ProjectService;
import com.javawwa25.app.springboot.task.controller.TaskController;
import com.javawwa25.app.springboot.task.dto.TaskDto;
import com.javawwa25.app.springboot.task.service.TaskService;
import com.javawwa25.app.springboot.user.dto.UserDto;
import com.javawwa25.app.springboot.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
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
	private TaskController underTest;

	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(underTest).build();

	}

	@Test
	void testProjectAllTasks() throws Exception {
		String expected = "/task/task-list";
		given(userService.getLoggedUserDto()).willReturn(UserDto.builder().build());
		given(taskService.getTypedTasksForProject(anyLong(),anyString())).willReturn(Set.of());
		long projectId = 1l;
		String type = "BUG";
		String actual = underTest.projectAllTasks(type, projectId, model);
		verify(model, times(3)).addAttribute(any(), any());
		verify(userService, times(1)).getLoggedUserDto();
		verify(taskService, times(1)).getTypedTasksForProject(projectId, type);
		assertEquals(expected, actual);
	}
	
	@Test
	void testProjectAllTasks_Request() throws Exception {
		String expected = "/task/task-list";
		mvc.perform(get(TASK_ENDPOINT, 1))
		.andExpect(status().isOk())
		.andExpect(view().name(expected));
	}
	
	@Test
	void testCreateTask() throws Exception {
		String expected = "/task/new_task";
		String actual = underTest.createTask("BUG", 1l, model);
		verify(model, times(5)).addAttribute(any(), any());
		verify(projectService, times(1)).getProjectNameById(1l);
		verify(userService, times(1)).getLoggedUserDto();
		verify(userService, times(1)).getSimpleDtos();
		assertEquals(expected, actual);
	}
	
	@Test
	void testCreateTask_Request() throws Exception {
		String expected = "/task/new_task";
		given(userService.getLoggedUserDto()).willReturn(UserDto.builder().build());
		mvc.perform(get(TASK_ENDPOINT + "/new?type=BUG", 1))
			.andExpect(status().isOk())
			.andExpect(view().name(expected));
	}

	@Test
	void testSaveTask() {
		String expected = "redirect:/projects/" + 1 + "?success";
		TaskDto dto = TaskDto.builder().userAssignedId(1l).build();
		String actual = underTest.saveTask(1l, dto);
		verify(taskService, times(1)).saveTask(dto);
		verify(projectService, times(1)).assignProject(anyLong(), anyLong());
		assertEquals(expected, actual);
	}
	
	@Test
	void testSaveTask_Request() throws Exception {
		String expected = "redirect:/projects/" + 1 + "?success";
		mvc.perform(post(TASK_ENDPOINT + "/new", 1)
				.flashAttr("task", TaskDto.builder().build()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(expected));
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

}
