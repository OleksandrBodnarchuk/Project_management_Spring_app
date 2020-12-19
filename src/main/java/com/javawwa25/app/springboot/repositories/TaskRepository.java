package com.javawwa25.app.springboot.repositories;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
