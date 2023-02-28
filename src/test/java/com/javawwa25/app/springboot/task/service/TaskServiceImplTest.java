package com.javawwa25.app.springboot.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javawwa25.app.springboot.account.Account;
import com.javawwa25.app.springboot.task.Status;
import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.task.TaskType;
import com.javawwa25.app.springboot.task.dto.TaskDto;
import com.javawwa25.app.springboot.task.repo.TaskRepository;
import com.javawwa25.app.springboot.user.User;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

	@Mock
	private TaskRepository taskRepository;
	private Set<Task> tasks = fillTasks();
	@InjectMocks
	private TaskServiceImpl underTest;

	@Test
	void testGetTypedTasksForProject_BUG() {
		Set<Task> expected = tasks.stream().filter(task -> task.getType().getName().equals("BUG"))
				.collect(Collectors.toSet());
		given(taskRepository.findAllByProjectIdAndTypeName(anyLong(), any())).willReturn(expected);

		Set<TaskDto> actual = underTest.getTypedTasksForProject(1, "BUG");
		assertEquals(expected.size(), actual.size());
	}
	
	@Test
	void testGetTypedTasksForProject_ALL() {
		underTest.getTypedTasksForProject(1l, "ALL");
		verify(taskRepository, never()).findAllByProjectIdAndTypeName(anyLong(), any());
		verify(taskRepository, times(1)).findAllByProjectId(anyLong());
	}

	private Set<Task> fillTasks() {
		Set<Task> result = new HashSet<>();
		for (int i = 0; i < 100; i++) {
			addTask(result, i);
		}
		return result;
	}

	private void addTask(Set<Task> result, int i) {
		String type = null;
		String progress = "NEW";
		if (i < 25) {
			type = "BUG";
		} else if (i >= 25 && i < 50) {
			type = "TASK";
		} else if (i >= 50 && i < 75) {
			type = "REQUIREMENT";
		} else if (i >= 75 && i < 100) {
			type = "SUPPORT";
		}
		if (i % 2 == 0) {
			progress = "IN_PROGRESS";
		}

		User assigned = User.builder().firstName("firstName" + i).lastName("lastName" + i)
				.account(Account.builder().accountId(Long.valueOf(i)).build()).build();

		User added = User.builder().firstName("firstNameAdded" + i).lastName("lastNameAdded" + i)
				.account(Account.builder().accountId(Long.valueOf(i)).build()).build();

		Task task = Task.builder().name("name" + i).description("description" + i)
				.taskType(TaskType.builder().name(type).build()).status(new Status(progress, 0)).userAssigned(assigned)
				.userAdded(added).modificationDate(new Date()).build();
		task.setId(Long.valueOf(i));
		result.add(task);
	}

}
