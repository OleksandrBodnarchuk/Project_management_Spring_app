package com.javawwa25.app.springboot.account.repo;

import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.account.Role;

public interface AuthorityRepository extends CrudRepository<Role, Long> {

	Role findByRole(String string);

}