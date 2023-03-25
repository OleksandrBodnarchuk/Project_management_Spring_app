package com.javawwa25.app.springboot.bootstrap;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.javawwa25.app.springboot.account.Account;
import com.javawwa25.app.springboot.account.Role;
import com.javawwa25.app.springboot.account.service.AccountService;
import com.javawwa25.app.springboot.comment.Comment;
import com.javawwa25.app.springboot.comment.CommentRepository;
import com.javawwa25.app.springboot.group.UserGroup;
import com.javawwa25.app.springboot.group.repo.GroupRepository;
import com.javawwa25.app.springboot.project.Project;
import com.javawwa25.app.springboot.task.Status;
import com.javawwa25.app.springboot.task.TaskType;
import com.javawwa25.app.springboot.task.repo.StatusRepository;
import com.javawwa25.app.springboot.task.service.TaskTypeService;
import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.service.RoleRepository;
import com.javawwa25.app.springboot.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final AccountService accountService;
	private final TaskTypeService taskTypeService;
	private final StatusRepository statusRepository;
	private final GroupRepository groupRepository;
	private final CommentRepository commentRepository;
	private final RoleRepository roleRepository;
	
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - Running DB initialization.");
		int count = userService.getAllUserDtos().size();
		Comment comment = new Comment(1L,2L,"TEST");
		commentRepository.save(comment);
		
		if (count == 0) {
			loadData();
		}

	}

	private void loadData() {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - loadData() - invoked\n\n");
		Account account1 = accountService.save(Account.builder()
							.email("admin@email.com")
							.password(passwordEncoder.encode("admin"))
							.registrationDate(new Date())
							.lastActiveDate(null)
							.isAdmin(Boolean.TRUE)
							.role(roleRepository.save(Role.builder().role("ADMIN").build()))
							.build());
		
		User user2 = userService.save(User.builder()
				.firstName("TempUser1")
				.lastName("TempUser1")
				.userStatus("Status is everything")
				.account(account1)
				.build());
		
		Account account2 = accountService.save(Account.builder()
				.email("user@email.com")
				.password(passwordEncoder.encode("user"))
				.registrationDate(new Date())
				.lastActiveDate(null)
				.isAdmin(Boolean.FALSE)
				.role(roleRepository.save(Role.builder().role("USER").build()))
				.build());
		
		User user = userService.save(User.builder()
				.firstName("TempUser2")
				.lastName("TempUser2")
				.userStatus("Status is everything")
				.account(account2)
				.build());
		
		Project project = Project.builder()
				.name("Project")
				.createdAt(new Date())
				.info("Description oth the proejct here")
				.build();
		
		project.addUser(user);
		project.addUser(user2);
		
		groupRepository.save(UserGroup.builder()
				.name("PM")
				.user(user)
				.user(user2)
				.project(project)
				.build());
		
		List<Status> defaultStatuses = List.of(
				new Status("NEW", 0), 
				new Status("IN_PROGRESS", 25), 
				new Status("QA", 50),
				new Status("VERIFIED", 60), 
				new Status("IMPLEMENTED", 75), 
				new Status("CLOSED", 100));
		
		TaskType task = taskTypeService.saveType(TaskType.builder()
				.name("TASK")
				.build());
		
		TaskType bug = taskTypeService.saveType(TaskType.builder()
				.name("BUG")
				.build());
		
		TaskType requirement = taskTypeService.saveType(TaskType.builder()
				.name("REQUIREMENT")
				.build());
		
		TaskType support = taskTypeService.saveType(TaskType.builder()
				.name("SUPPORT")
				.build());

		for (Status status : defaultStatuses) {
			task.addStatus(status);
			statusRepository.save(status);
			bug.addStatus(status);
			statusRepository.save(status);
			requirement.addStatus(status);
			statusRepository.save(status);
			support.addStatus(status);
			statusRepository.save(status);
		}
	}
}
