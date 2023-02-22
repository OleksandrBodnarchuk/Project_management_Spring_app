package com.javawwa25.app.springboot.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.web.dto.ProjectDto;

@Service
public interface ProjectService {
	List<Project> getAllProjects();

	void save(ProjectDto dto);

	Project getProjectById(long id);

	String getProjectNameById(long id);
	
	void deleteProjectById(long id);

	List<ProjectDto> getProjectDtosByAccountId(long id);
	
	List<ProjectDto> getProjectsNotPartOf(long accountId);

	void assignProject(long accountId, long projectId);

	Set<Task> getProjectTasks(long projectId);

	ProjectDto getProjectDtoById(long projectId);

}
