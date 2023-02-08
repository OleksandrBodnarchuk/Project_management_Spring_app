package com.javawwa25.app.springboot.bootstrap;


import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.UserService;

@Component
public class DataLoader implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
	
	private final UserService userService;
	private final ProjectService projectService;
	
	public DataLoader(UserService userService, ProjectService projectService) {
		this.userService = userService;
		this.projectService = projectService;
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - Running DB initialization.");
		int count = userService.getAllUsers().size();
		if (count == 0) {
			loadData();
		}

	}

	private void loadData() {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - loadData() - invoked");
		Project javaProject = Project.builder()
				.startDate(LocalDate.now())
				.name("Java")
				.info("Description")
				.build();
		
		Project pythonProject = Project.builder()
				.startDate(LocalDate.now())
				.name("Python")
				.info("Description")
				.build();
		projectService.save(pythonProject);
		projectService.save(javaProject);
		
		User tempUser1 = User.builder()
				.firstName("TempUser1")
				.lastName("TempUser1")
				.email("tempUser1@email.com")
				.password("tempUser1")
				.build();
		
		User tempUser2 = User.builder()
				.firstName("TempUser2")
				.lastName("TempUser2")
				.email("tempUser2@email.com")
				.password("tempUser2")
				.build();
		
		userService.save(tempUser1);
		userService.save(tempUser2);
		
		linkUserWithProject(pythonProject, tempUser1);
		linkUserWithProject(pythonProject, tempUser2);
		linkUserWithProject(javaProject, tempUser1);
		
		LOG.debug("[Users saved]:\n\t - " + tempUser1.getEmail() + "\n\t - " + tempUser2.getEmail());
		LOG.debug("[Projects saved]:\n\t - " + javaProject.getName() + "\n\t - " + pythonProject.getName());
		
	}

	private void linkUserWithProject(Project project, User user) {
		user.addProject(project);
		project.addUser(user);
	}

}
