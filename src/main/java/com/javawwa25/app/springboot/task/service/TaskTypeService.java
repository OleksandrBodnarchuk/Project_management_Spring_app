package com.javawwa25.app.springboot.task.service;

import java.util.Set;

import com.javawwa25.app.springboot.task.TaskType;

public interface TaskTypeService {

	TaskType saveType(TaskType taskType);

	TaskType findByName(String type);
	
	Set<String> findAllTypeNames();

}
