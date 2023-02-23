package com.javawwa25.app.springboot.task.service;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.task.repo.StatusRepository;
import com.javawwa25.app.springboot.task.Status;

@Service
public class StatusServiceImpl implements StatusService {

	private final StatusRepository statusRepository;

	public StatusServiceImpl(StatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}

	@Override
	public Status saveStatus(Status status) {
		return statusRepository.save(status);
	}

}
