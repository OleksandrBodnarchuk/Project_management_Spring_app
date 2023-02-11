package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.web.dto.UserDto;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

public interface UserService {
	List<User> getAllUsers();

	User getUserById(long id);

	void deleteUserById(long id);

	User saveRegister(UserRegistrationDto registrationDto);

	User save(User user);

	User findByEmail(String email);

	Long getLoggedUserId();

	void userLogged();

	void fillUserDtoModel(long userId, Model model);

	void update(UserDto dto);
}
