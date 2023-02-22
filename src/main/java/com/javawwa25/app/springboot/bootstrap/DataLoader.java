package com.javawwa25.app.springboot.bootstrap;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.javawwa25.app.springboot.models.Account;
import com.javawwa25.app.springboot.models.Authority;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.AuthorityRepository;
import com.javawwa25.app.springboot.repositories.ProjectRepository;
import com.javawwa25.app.springboot.services.AccountService;
import com.javawwa25.app.springboot.services.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
	
	private final UserService userService;
	private final ProjectRepository projectRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthorityRepository authorityRepository;
	private final AccountService accountService;
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - Running DB initialization.");
		int count = userService.getAllUserDtos().size();
		if (count == 0) {
			loadData();
		}

	}

	private void loadData() {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - loadData() - invoked\n\n");
		Account account1 = accountService.save(Account.builder()
							.email("tempUser1@email.com")
							.password(passwordEncoder.encode("tempUser1"))
							.registrationDate(new Date())
							.lastActiveDate(null)
							.authority(authorityRepository.save(Authority.builder().role("ADMIN").build()))
							.build());
		
		userService.save(User.builder()
				.firstName("TempUser1")
				.lastName("TempUser1")
				.userStatus("Status is everything")
				.account(account1)
				.build());
		
		Account account2 = accountService.save(Account.builder()
				.email("tempUser2@email.com")
				.password(passwordEncoder.encode("tempUser2"))
				.registrationDate(new Date())
				.lastActiveDate(null)
				.authority(authorityRepository.save(Authority.builder().role("USER").build()))
				.build());
		
		userService.save(User.builder()
				.firstName("TempUser2")
				.lastName("TempUser2")
				.userStatus("Status is everything")
				.account(account2)
				.build());
		
		projectRepository.save(Project.builder()
				.name("Project")
				.createdAt(new Date())
				.info("Description oth the proejct here")
				.build());
	
	}

}
