package com.javawwa25.app.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javawwa25.app.springboot.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByUsers_Id(long userId);

}
