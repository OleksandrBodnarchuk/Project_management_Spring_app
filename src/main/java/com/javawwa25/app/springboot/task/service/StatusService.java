package com.javawwa25.app.springboot.task.service;

import com.javawwa25.app.springboot.task.Status;

public interface StatusService {

	Status saveStatus(Status status);

	Status findByName(String status);
}
