package com.javawwa25.app.springboot.account.repo;

import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.account.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

	Authority findByRole(String string);

}