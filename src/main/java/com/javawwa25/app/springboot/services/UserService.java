package com.javawwa25.app.springboot.services;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
	List<User> getAllUsers();
	void saveUser(User user);
	User getUserById(long id);
	void deleteUserById(long id);

	// method to save User during registration
	User save(UserRegistrationDto registrationDto);

}


