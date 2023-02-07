package com.javawwa25.app.springboot.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javawwa25.app.springboot.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t FROM Task t where t.progress = 'TODO' and t.project.id=?1")
    List<Task> getAllTasksOnTODOStep(long project_id);
    @Query(value = "SELECT t FROM Task t where t.progress = 'IN_PROGRESS' and t.project.id=?1")
    List<Task> getAllTasksOnInProgressStep(long project_id);
    @Query(value = "SELECT t FROM Task t where t.progress = 'QA' and t.project.id=?1")
    List<Task> getAllTasksOnQAStep(long project_id);
    @Query(value = "SELECT t FROM Task t where t.progress = 'DONE' and t.project.id=?1")
    List<Task> getAllTasksOnDONEStep(long project_id);
    @Query(value = "SELECT t FROM Task t where t.project.user.id=?1")
    List<Task> getAllTasksByUserId(long id);

    @Query(value = "SELECT t FROM Task t where t.user.id=?1")
    List<Task> getAllTasksByUser(long id);


}
