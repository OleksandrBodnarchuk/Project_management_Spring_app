package com.javawwa25.app.springboot.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.ProjectRepository;
import com.javawwa25.app.springboot.web.dto.ProjectDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final static Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

	private final ProjectRepository projectRepository;
	private final UserService userService;

	@Override
	public List<Project> getAllProjects() {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - getAllProjects - called");
		return projectRepository.findAll();
	}

	@Override
	public void save(ProjectDto dto) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - save - called");
		this.projectRepository
				.save(Project.builder().name(dto.getName()).info(dto.getInfo()).created(LocalDate.now()).build());
	}

	@Override
	public Project getProjectById(long id) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - getProjectById - called");
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
		User user = userService.getUserByAccountId(accountId);
		return projectRepository
				.findNotUsers(user.getProjects().stream()
						.map(Project::getId)
						.collect(Collectors.toList()))
				.stream()
				.map(this::mapProjectToDto)
				.collect(Collectors.toList());
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

}
