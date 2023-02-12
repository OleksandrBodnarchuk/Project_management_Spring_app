package com.javawwa25.app.springboot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.models.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {

}
