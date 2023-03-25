package com.javawwa25.app.springboot.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.user.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;

	public Role getById(long id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No role found with id: " + id));
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public void saveRole(Role role) {
		roleRepository.save(role);
	}
	
}
