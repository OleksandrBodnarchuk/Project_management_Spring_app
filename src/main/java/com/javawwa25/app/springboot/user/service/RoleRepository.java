package com.javawwa25.app.springboot.user.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javawwa25.app.springboot.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
