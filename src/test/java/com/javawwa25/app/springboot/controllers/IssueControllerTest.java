package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class IssueControllerTest {

	@Test
	void testCreateTask() {
		String template = "/task/new_task";
		fail("Not yet implemented");
	}

	@Test
	void testSaveTask() {
		String template = "redirect:/users/{id}";
		fail("Not yet implemented");
	}

	@Test
	void testShowTaskInfo() {
		String template = "/task/task-info";
		fail("Not yet implemented");
	}

	@Test
	void testUpdateTaskInBoard() {
		String template = "/task/update_task";
		fail("Not yet implemented");
	}

	@Test
	void testMoveTaskToNextStep() {
		String template;
		fail("Not yet implemented");
	}

	@Test
	void testMoveTaskToPreviousStep() {
		long userId = 1l;
		long projectId = 1l;
		String template = "redirect:/users/" + userId + "/projects/" + projectId + "/tasks";
		;
		fail("Not yet implemented");
	}

	@Test
	void testAllTasks() {
		String template = "/task/task-list";
		fail("Not yet implemented");
	}

}
