package com.javawwa25.app.springboot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javawwa25.app.springboot.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
