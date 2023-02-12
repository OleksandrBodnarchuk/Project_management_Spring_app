package com.javawwa25.app.springboot.services;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.TaskType;
import com.javawwa25.app.springboot.repositories.TaskTypeRepository;

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
