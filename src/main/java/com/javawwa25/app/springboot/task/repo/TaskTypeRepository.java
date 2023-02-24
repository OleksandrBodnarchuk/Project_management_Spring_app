package com.javawwa25.app.springboot.task.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.task.TaskType;

public interface TaskTypeRepository extends CrudRepository<TaskType, Long>{

	TaskType findByName(String upperCase);

	@Query("SELECT t.name FROM TaskType t")
	Set<String> findAllTypeNames();

}
