package com.javawwa25.app.springboot.services;

import java.util.List;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

public interface UserService /*extends UserDetailsService*/ {
	List<User> getAllUsers();
	User getUserById(long id);
	void deleteUserById(long id);

	// method to save User during registration
	User save(UserRegistrationDto	 registrationDto);
	User findByEmail(String email);

}


