package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.web.dto.ProjectDto;

@Service
public interface ProjectService {
	List<Project> getAllProjects();

	void save(ProjectDto dto);

	Project getProjectById(long id);

	void deleteProjectById(long id);

	List<Project> getAllProjectsByUserId(long user_id);

	void fillDtoProjectsModel(Model model);

	void fillAllProjectsForAdmin(Model model);
}
