package com.javawwa25.app.springboot.repositories;


import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t FROM Task t where t.task_progress = 'TODO' and t.project.project_id=?1")
    List<Task> getAllTasksOnTODOStep(long project_id);
    @Query(value = "SELECT t FROM Task t where t.task_progress = 'IN_PROGRESS' and t.project.project_id=?1")
    List<Task> getAllTasksOnInProgressStep(long project_id);
    @Query(value = "SELECT t FROM Task t where t.task_progress = 'QA' and t.project.project_id=?1")
    List<Task> getAllTasksOnQAStep(long project_id);
    @Query(value = "SELECT t FROM Task t where t.task_progress = 'DONE' and t.project.project_id=?1")
    List<Task> getAllTasksOnDONEStep(long project_id);
    @Query(value = "SELECT t FROM Task t where t.project.user.user_id=?1")
    List<Task> getAllTasksByUserId(long user_id);

    @Query(value = "SELECT t FROM Task t where t.user.user_id=?1")
    List<Task> getAllTasksByUser(long user_id);


}
