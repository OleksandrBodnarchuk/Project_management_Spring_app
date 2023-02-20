package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.web.dto.UserDto;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

public interface UserService {
	List<UserDto> getAllUserDtos();

	User getUserById(long id);

	void deleteUserById(long id);

	User saveRegister(UserRegistrationDto registrationDto);

	User save(User user);
	
	User save(UserDto dto);

	User findByEmail(String email);

	User getLoggedUser();

	void userLogged();

	void update(UserDto dto);

	void fillAllUsersForAdmin(Model model);
	
	void fillUserDtoModel(Model model);
	
	void fillUserDtoRegistrationModel(Model model);
	
	UserDto getLoggedUserDto();

	void fillUserDtoRegistrationModel(UserRegistrationDto dto, Model model);

	void fillAdminUserDtoModel(long id, Model model);
	
}
