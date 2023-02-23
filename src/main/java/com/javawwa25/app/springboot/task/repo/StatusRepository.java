package com.javawwa25.app.springboot.task.repo;

import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.task.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {

}
