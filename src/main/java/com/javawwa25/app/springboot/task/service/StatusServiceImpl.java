package com.javawwa25.app.springboot.task.service;

import java.util.Optional;
import java.util.Set;

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
		Optional<Status> exists = statusRepository.findByName(status.getName());
		if(exists.isEmpty()) {
			return statusRepository.save(status);
		}
		return exists.get();
	}

	@Override
	public Status findByName(String name) {
		return statusRepository.findByName(name)
				.orElseThrow(()-> new RuntimeException("Status "+ name+" not found!"));
	}

	@Override
	public Set<Status> findOpenStatuses() {
		return statusRepository.findByPercentageNot(100);
	}

	@Override
	public Set<Status> findClosedStatuses() {
		return statusRepository.findByPercentageIs(100);
	}

	@Override
	public Set<Status> getAll() {
		return Set.copyOf(statusRepository.findAll());
	}

}
