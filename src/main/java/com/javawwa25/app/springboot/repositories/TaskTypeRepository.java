package com.javawwa25.app.springboot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.models.TaskType;

public interface TaskTypeRepository extends CrudRepository<TaskType, Long>{

}
