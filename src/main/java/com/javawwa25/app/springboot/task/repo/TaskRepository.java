package com.javawwa25.app.springboot.task.repo;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.task.dto.ProjectTaskDetails;

public interface TaskRepository extends JpaRepository<Task, Long> {

	Set<Task> findAllByUserAddedId(long userId);

	Set<Task> findAllByUserAssignedId(long userId);

	Set<Task> findAllByProjectId(long projectId);
	
//	@Query("SELECT DISTINCT(main.name) as type, "
//			+ "(SELECT COUNT(t.id) as open FROM Task t "
//			+ "WHERE t.status.id IN "
//			+ "(SELECT s.id FROM Status s WHERE s.percentage != 100) "
//			+ "AND t.type.id = (SELECT main.id FROM TaskType main WHERE main.name = main.name) "
//			+ "AND t.project.id = :projectId) as open, "
//			+ "(SELECT COUNT(t.id) as closed FROM Task t  "
//			+ "WHERE t.status.id IN(SELECT s.id FROM Status s WHERE s.percentage = 100) "
//			+ "AND t.type.id = (SELECT type.id FROM TaskType type WHERE type.name = main.name) "
//			+ "AND t.project.id = :projectId) as closed "
//			+ "FROM TaskType main")
	@Query(nativeQuery = true, value = "SELECT DISTINCT(tt.name) AS type, "
			+ "(SELECT COUNT(t.id) FROM task t  "
			+ "WHERE t.status_id IN(SELECT s.id FROM status s WHERE s.percentage !=100) "
			+ "AND t.type_id = (SELECT s.id FROM task_type s WHERE s.name =tt.name) "
			+ "AND t.project_id = :projectId) AS open, "
			+ "(SELECT COUNT(t.id) FROM task t  "
			+ "WHERE t.status_id IN(SELECT s.id FROM status s WHERE s.percentage =100) "
			+ "AND t.type_id = (SELECT s.id FROM task_type s WHERE s.name =tt.name) "
			+ "AND t.project_id = :projectId) AS closed "
			+ "FROM task_type tt")
	Set<ProjectTaskDetails> getProjectTaskDetails(@Param(value = "projectId") long projectId);

	Set<Task> findAllByProjectIdAndTypeName(long projectId, String type);

}
