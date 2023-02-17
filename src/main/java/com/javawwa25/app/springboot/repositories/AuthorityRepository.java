package com.javawwa25.app.springboot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.models.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

}