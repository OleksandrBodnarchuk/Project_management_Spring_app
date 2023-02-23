package com.javawwa25.app.springboot.task.service;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.task.repo.TaskTypeRepository;
import com.javawwa25.app.springboot.task.TaskType;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {

	private final TaskTypeRepository taskTypeRepository;
	
	public TaskTypeServiceImpl(TaskTypeRepository taskTypeRepository) {
		this.taskTypeRepository = taskTypeRepository;
	}

	@Override
	public TaskType saveType(TaskType taskType) {
		return taskTypeRepository.save(taskType);
	}
}
