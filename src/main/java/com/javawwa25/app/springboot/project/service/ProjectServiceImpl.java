package com.javawwa25.app.springboot.project.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.project.Project;
import com.javawwa25.app.springboot.project.dto.ProjectDto;
import com.javawwa25.app.springboot.project.repo.ProjectRepository;
import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.task.service.TaskService;
import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final static Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

	private final ProjectRepository projectRepository;
	private final UserService userService;
	private final TaskService taskService;

	@Override
	public List<Project> getAllProjects() {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - getAllProjects - called");
		return projectRepository.findAll();
	}

	@Override
	public void save(ProjectDto dto) {
		this.projectRepository
				.save(Project.builder().name(dto.getName())
						.info(dto.getInfo()).createdAt(new Date()).build());
	}

	@Override
	public Project getProjectById(long id) {
		return projectRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Project not found for id : " + id));
	}

	@Override
	public void deleteProjectById(long id) {
		this.projectRepository.deleteById(id);
	}

	@Override
	public List<ProjectDto> getProjectDtosByAccountId(long accountId) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - getProjectDtosByAccountId - called");
		User user = userService.getUserByAccountId(accountId);
		return user.getProjects().stream()
				.map(this::mapProjectToDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ProjectDto> getProjectsNotPartOf(long accountId) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - getProjectsNotPartOf - called");
		User user = userService.getUserByAccountId(accountId);
		if(user.getProjects().isEmpty()) {
			return projectRepository.findAll().stream()
					.map(this::mapProjectToDto)
					.collect(Collectors.toList());
		} else {
			return projectRepository
					.findNotUsers(user.getProjects().stream()
							.map(Project::getId)
							.collect(Collectors.toList())).stream()
					.map(this::mapProjectToDto)
					.collect(Collectors.toList());
		}
	}

	private ProjectDto mapProjectToDto(Project p) {
		return ProjectDto.builder()
				.id(p.getId())
				.createdAt(p.getCreatedAt())
				.info(p.getInfo())
				.name(p.getName())
				.build();
	}

	@Override
	public void assignProject(long accountId, long projectId) {
		User user = userService.getUserByAccountId(accountId);
		Project project = getProjectById(projectId);
		project.addUser(user);
		projectRepository.save(project);
	}

	@Override
	public Set<Task> getProjectTasks(long projectId) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - getProjectTasks - called");
		return getProjectById(projectId).getTasks();
	}

	@Override
	public ProjectDto getProjectDtoById(long projectId) {
		Project project = getProjectById(projectId);
		ProjectDto dto = ProjectDto.builder()
			.name(project.getName())
			.id(projectId)
			.tasks(taskService.getProjectTaskDetails(projectId))
			.build();
		return dto;
	}

	@Override
	public String getProjectNameById(long id) {
		return projectRepository.findNameById(id);
	}

}