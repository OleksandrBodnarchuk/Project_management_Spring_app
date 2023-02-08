package com.javawwa25.app.springboot.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

@Component
public class DataLoader implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
	
	private final UserService userService;
	
	public DataLoader(UserService userService) {
		this.userService = userService;
	}

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
		UserRegistrationDto tempUser1 = UserRegistrationDto.builder()
				.firstName("TempUser1")
				.lastName("TempUser1")
				.email("tempUser1@email.com")
				.password("tempUser1")
				.build();
		
		UserRegistrationDto tempUser2 = UserRegistrationDto.builder()
				.firstName("TempUser2")
				.lastName("TempUser2")
				.email("tempUser2@email.com")
				.password("tempUser2")
				.build();
		
		userService.save(tempUser1);
		userService.save(tempUser2);
		
		LOG.debug("[Users saved]:\n\t - " + tempUser1.getEmail() + "\n\t - " + tempUser2.getEmail());
	}

}
