package com.javawwa25.app.springboot.services;

import java.util.List;
import java.util.Set;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.web.dto.UserDto;
import com.javawwa25.app.springboot.web.dto.UserDtoName;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

public interface UserService {
	List<UserDto> getAllUserDtos();

	User getUserById(long id);
	
	User getUserByAccountId(long id);

	void deleteUserById(long id);

	User saveRegister(UserRegistrationDto registrationDto);

	User save(User user);
	
	User save(UserDto dto);

	User findByEmail(String email);

	User getLoggedUser();

	void userLogged();

	void update(UserDto dto);

	UserDto getLoggedUserDto();

	void updateUser(UserDto dto);

	UserDto getUserDtoByAccountId(long accountId);

	Set<UserDtoName> getDtoUserNames();
	
}
