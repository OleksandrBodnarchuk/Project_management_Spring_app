package com.javawwa25.app.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javawwa25.app.springboot.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Query("SELECT p FROM Project p JOIN FETCH p.users WHERE p.id = :id")
	List<Project> findAllUserProjects(@Param("id") long userId);

}
