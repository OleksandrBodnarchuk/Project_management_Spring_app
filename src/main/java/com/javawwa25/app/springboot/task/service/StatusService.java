package com.javawwa25.app.springboot.task.service;

import java.util.Set;

import com.javawwa25.app.springboot.task.Status;

public interface StatusService {

	Status saveStatus(Status status);

	Status findByName(String status);

	Set<Status> findOpenStatuses();

	Set<Status> findClosedStatuses();

	Set<Status> getAll();
}
