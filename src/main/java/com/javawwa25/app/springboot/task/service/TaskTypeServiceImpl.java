package com.javawwa25.app.springboot.task.service;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.task.TaskType;
import com.javawwa25.app.springboot.task.repo.TaskTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskTypeServiceImpl implements TaskTypeService {

	private final TaskTypeRepository taskTypeRepository;
	
	@Override
	public TaskType saveType(TaskType taskType) {
		return taskTypeRepository.save(taskType);
	}

	@Override
	public TaskType findByName(String type) {
		return taskTypeRepository.findByName(type.toUpperCase());
	}

	@Override
	public Set<String> findAllTypeNames() {
		return taskTypeRepository.findAllTypeNames();
	}
}
