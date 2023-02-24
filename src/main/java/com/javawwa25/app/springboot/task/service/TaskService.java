package com.javawwa25.app.springboot.task.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.task.dto.TaskDto;
import com.javawwa25.app.springboot.task.Task;

@Service
public interface TaskService {
	List<Task> getAllTasks();

	void saveTask(TaskDto dto);

	void save(Task task);
	
	Task getTaskById(long id);

	void deleteTaskById(long id);

	Set<Task> getAssignedTasksForUser();

	Set<Task> getCreatedTasksForUser();

	Set<Task> getAllTasksByProjectId(long projectId);

}
