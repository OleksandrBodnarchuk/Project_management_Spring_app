package com.javawwa25.app.springboot.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.web.dto.TaskDto;

@Service
public interface TaskService {
	List<Task> getAllTasks();

	void saveTask(TaskDto task);

	Task getTaskById(long id);

	void deleteTaskById(long id);

	Set<Task> getAssignedTasksForUser();

	Set<Task> getCreatedTasksForUser();

}
