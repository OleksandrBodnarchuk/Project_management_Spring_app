package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.User;

@Service
public interface UserService<T extends User> /*extends UserDetailsService*/ {
	List<T> getAllUsers();
	void saveUser(T user);
	T getUserById(long id);
	void deleteUserById(long id);

	// method to save User during registration
	T save(T registrationDto);

}


