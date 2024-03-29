package com.javawwa25.app.springboot.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javawwa25.app.springboot.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByUsers_Id(long userId);

	List<Project> findByUsers_IdNot(Long id);

	@Query("SELECT p FROM Project p WHERE p.id NOT in (:id)")
	List<Project> findNotUsers(@Param("id") List<Long> id);

	@Query("SELECT p.name FROM Project p WHERE p.id = :id")
	String findNameById(@Param("id") long id);

}
