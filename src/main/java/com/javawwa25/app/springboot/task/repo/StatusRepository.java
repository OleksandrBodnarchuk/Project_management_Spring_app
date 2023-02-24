package com.javawwa25.app.springboot.task.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javawwa25.app.springboot.task.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

	Optional<Status> findByName(String name);

}
