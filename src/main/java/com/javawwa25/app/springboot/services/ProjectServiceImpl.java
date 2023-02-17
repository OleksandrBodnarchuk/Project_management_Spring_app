package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.ProjectRepository;
import com.javawwa25.app.springboot.web.dto.UserDto;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final UserService userService;

	@Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void save(Project project) {
        this.projectRepository.save(project);
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
    public List<Project> getAllProjectsByUserId(long userId) {
        List<Project> projectList = projectRepository.findByUsers_Id(userId);
        return projectList;
    }

	@Override
	public void fillDtoProjectsModel(long userId, Model model) {
		User user = userService.getUserById(userId);
		UserDto dto = new UserDto();
		dto.setAccountId(user.getAccount().getAccountId());
		dto.setEmail(user.getAccount().getEmail());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		model.addAttribute("user", dto);
        model.addAttribute("projects", projectRepository.findByUsers_Id(userId));
	}

}
