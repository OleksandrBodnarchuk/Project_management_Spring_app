package com.javawwa25.app.springboot.user.service;

import java.util.List;

import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.dto.GroupDto;
import com.javawwa25.app.springboot.user.dto.SimpleUserDto;
import com.javawwa25.app.springboot.user.dto.UserDto;
import com.javawwa25.app.springboot.user.dto.UserRegistrationDto;

public interface UserService {
	List<UserDto> getAllUserDtos();

	User getUserById(long id);
	
	User getUserByAccountId(long id);

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

	List<SimpleUserDto> getSimpleDtos();

	List<SimpleUserDto> getAllUsersForGroup(GroupDto group);
	
}
