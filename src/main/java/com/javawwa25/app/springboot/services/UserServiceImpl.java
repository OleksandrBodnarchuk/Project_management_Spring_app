package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUserById(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public User save(UserRegistrationDto dto) {
		User detachedUser = User.builder().firstName(dto.getFirstName()).lastName(dto.getLastName())
				.email(dto.getEmail()).password(dto.getPassword()).build();
		return userRepository.save(detachedUser);
	}

}
