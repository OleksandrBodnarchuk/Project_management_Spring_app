package com.javawwa25.app.springboot.task.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javawwa25.app.springboot.task.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

	Optional<Status> findByName(String name);

	Set<Status> findByPercentageNot(int i);

	Set<Status> findByPercentageIs(int i);

}
