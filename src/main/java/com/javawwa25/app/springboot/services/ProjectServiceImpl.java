package com.javawwa25.app.springboot.services;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.repositories.ProjectRepository;
import com.javawwa25.app.springboot.web.dto.ProjectDto;
import com.javawwa25.app.springboot.web.dto.UserDto;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

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
				.save(Project.builder()
						.name(dto.getName())
						.info(dto.getInfo())
						.created(LocalDate.now())
						.build());
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
    public List<Project> getAllProjectsByUserId(long userId) {
    	LOG.debug("[" + this.getClass().getSimpleName() + "] - getAllProjectsByUserId - called");
        List<Project> projectList = projectRepository.findByUsers_Id(userId);
        return projectList;
    }

	@Override
	public void fillDtoProjectsModel(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - fillDtoProjectsModel - called");
		UserDto dto = userService.getLoggedUserDto();
		model.addAttribute("user", dto);
        model.addAttribute("projects", projectRepository.findByUsers_Id(userService.getLoggedUser().getId()));
	}

	@Override
	public void fillAllProjectsForAdmin(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - fillAllProjectsForAdmin - called");
		UserDto dto = userService.getLoggedUserDto();
		model.addAttribute("user", dto);
		model.addAttribute("projectList", getAllProjects());
	}

}
