package com.javawwa25.app.springboot.repositories;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javawwa25.app.springboot.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	Set<Task> findAllByUserAddedId(long userId);

	Set<Task> findAllByUserAssignedId(long userId);

	Set<Task> findAllByProjectId(long projectId);

}
