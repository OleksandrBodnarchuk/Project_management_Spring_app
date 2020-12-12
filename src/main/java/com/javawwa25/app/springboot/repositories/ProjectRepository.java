package com.javawwa25.app.springboot.repositories;

import com.javawwa25.app.springboot.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Long> {

}
