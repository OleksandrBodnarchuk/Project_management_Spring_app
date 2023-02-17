package com.javawwa25.app.springboot.bootstrap;


import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.javawwa25.app.springboot.models.Account;
import com.javawwa25.app.springboot.models.Authority;
import com.javawwa25.app.springboot.models.Priority;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.Status;
import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.models.TaskType;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.AuthorityRepository;
import com.javawwa25.app.springboot.services.AccountService;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.StatusService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.TaskTypeService;
import com.javawwa25.app.springboot.services.UserService;

@Component
public class DataLoader implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
	
	private final UserService userService;
	private final ProjectService projectService;
	private final PasswordEncoder passwordEncoder;
	private final TaskTypeService taskTypeService;
	private final TaskService taskService;
	private final StatusService statusService;
	private final AuthorityRepository authorityRepository;
	private final AccountService accountService;
	
	public DataLoader(UserService userService, ProjectService projectService, PasswordEncoder passwordEncoder,
			TaskTypeService taskTypeService, TaskService taskService, StatusService statusService,
			AccountService accountService, AuthorityRepository authorityRepository) {
		this.userService = userService;
		this.projectService = projectService;
		this.passwordEncoder = passwordEncoder;
		this.taskTypeService = taskTypeService;
		this.taskService = taskService;
		this.statusService = statusService;
		this.authorityRepository = authorityRepository;
		this.accountService = accountService;
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
		LOG.debug("[" + this.getClass().getSimpleName() + "] - loadData() - invoked\n\n");
		LOG.debug("[SAVING TASK TYPES]");
		TaskType bug = new TaskType();
		bug.setName("BUG");
		taskTypeService.saveType(bug);
		TaskType task = new TaskType();
		bug.setName("TASK");
		taskTypeService.saveType(task);
		// SAVING TASK STATUSES PER TYPE		
		Status newStatus = Status.builder().name("NEW").taskType(bug).build();
		Status inProgressStatus = Status.builder().name("IN_PROGRESS").taskType(bug).build();
		Status qaStatus = Status.builder().name("QA").taskType(bug).build();
		Status doneStatus = Status.builder().name("DONE").taskType(bug).build();
		statusService.saveStatus(newStatus);
		statusService.saveStatus(inProgressStatus);
		statusService.saveStatus(qaStatus);
		statusService.saveStatus(doneStatus);

		LOG.debug("[SAVING PROJECTS]");
		Project javaProject = Project.builder()
				.created(LocalDate.now())
				.name("Java")
				.info("Description")
				.build();
		
		Project pythonProject = Project.builder()
				.created(LocalDate.now())
				.name("Python")
				.info("Description")
				.build();
		projectService.save(pythonProject);
		projectService.save(javaProject);
		
		Authority admin = authorityRepository.save(Authority.builder().role("ADMIN").build());
		Authority user = authorityRepository.save(Authority.builder().role("USER").build());
		
		LOG.debug("[SAVING ACCOUNT 2]");
		Account account1 = accountService.save(Account.builder()
							.email("tempUser1@email.com")
							.password(passwordEncoder.encode("tempUser1"))
							.registrationDate(LocalDate.now())
							.lastActiveDate(null)
							.authority(admin)
							.build());
		
		User tempUser1 = userService.save(User.builder()
				.firstName("TempUser1")
				.lastName("TempUser1")
				.userStatus("Status is everything")
				.account(account1)
				.build());
		
		LOG.debug("[SAVING ACCOUNT 2]");
		Account account2 = accountService.save(Account.builder()
				.email("tempUser2@email.com")
				.password(passwordEncoder.encode("tempUser2"))
				.registrationDate(LocalDate.now())
				.lastActiveDate(null)
				.authority(user)
				.build());
		
		User tempUser2 = userService.save(User.builder()
				.firstName("TempUser2")
				.lastName("TempUser2")
				.userStatus("Status is everything")
				.account(account2)
				.build());
		
		LOG.debug("[SAVING USERS]");
		LOG.debug("[LINKING PROJECTS WITH USERS]");
		linkUserWithProject(pythonProject, tempUser1);
		linkUserWithProject(pythonProject, tempUser2);
		linkUserWithProject(javaProject, tempUser1);
		
		Task task1 = Task.builder()
				.createdAt(LocalDate.now())
				.description("Error 500 fix")
				.name("TASK 1 - NAME")
				.priority(Priority.HIGH)
				.project(javaProject)
				.taskType(bug)
				.userAdded(tempUser1)
				.userAssigned(tempUser2)
				.status(inProgressStatus)
				.build();
		
		Task task2 = Task.builder()
				.createdAt(LocalDate.now())
				.description("Error 500 fix")
				.name("TASK 2 - NAME")
				.priority(Priority.HIGH)
				.project(javaProject)
				.taskType(bug)
				.userAdded(tempUser2)
				.userAssigned(tempUser1)
				.status(inProgressStatus)
				.build();
		taskService.saveTask(task1);
		taskService.saveTask(task2);
		
	}

	private void linkUserWithProject(Project project, User user) {
		user.addProject(project);
		project.addUser(user);
	}

}
