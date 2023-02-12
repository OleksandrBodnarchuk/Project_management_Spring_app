package com.javawwa25.app.springboot.services;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Status;
import com.javawwa25.app.springboot.repositories.StatusRepository;

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
