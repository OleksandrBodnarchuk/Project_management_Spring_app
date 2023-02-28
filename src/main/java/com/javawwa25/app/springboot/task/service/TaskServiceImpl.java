package com.javawwa25.app.springboot.task.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.project.repo.ProjectRepository;
import com.javawwa25.app.springboot.task.Status;
import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.task.TaskType;
import com.javawwa25.app.springboot.task.dto.ProjectTaskDetails;
import com.javawwa25.app.springboot.task.dto.TaskDto;
import com.javawwa25.app.springboot.task.repo.TaskRepository;
import com.javawwa25.app.springboot.user.service.UserService;
import com.javawwa25.app.springboot.utils.CommonUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final UserService userService;
	private final TaskTypeService taskTypeService;
	private final StatusService statusService;
	private final ProjectRepository projectRepository;

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@Override
	public void saveTask(TaskDto dto) {
		Status status = statusService.findByName("NEW");
		TaskType taskType = taskTypeService.findByName(dto.getType());

		this.taskRepository.save(Task.builder().name(dto.getName()).description(dto.getDescription())
				.priority(dto.getPriority()).taskType(taskType).status(status).startDate(dto.getStartDate())
				.modificationDate(dto.getModificationDate())
				.userAssigned(userService.getUserByAccountId(dto.getUserAssignedId()))
				.userAdded(userService.getLoggedUser()).endDate(dto.getEndDate()).createdAt(dto.getCreatedAt())
				.project(projectRepository.findById(dto.getProjectId())
						.orElseThrow(() -> new RuntimeException("Project not found for id : " + dto.getProjectId())))
				.build());
	}

	@Override
	public Task getTaskById(long id) {
		Optional<Task> optional = taskRepository.findById(id);
		Task task = null;
		if (optional.isPresent()) {
			task = optional.get();
		} else {
			throw new RuntimeException("Task not found for id :: " + id);
		}
		return task;
	}

	@Override
	public void deleteTaskById(long id) {
		this.taskRepository.deleteById(id);
	}

	@Override
	public Set<Task> getCreatedTasksForUser() {
		return taskRepository.findAllByUserAddedId(userService.getLoggedUser().getId());
	}

	@Override
	public Set<Task> getAssignedTasksForUser() {
		return taskRepository.findAllByUserAssignedId(userService.getLoggedUser().getId());

	}

	@Override
	public Set<Task> getAllTasksByProjectId(long projectId) {
		return taskRepository.findAllByProjectId(projectId);
	}

	@Override
	public void save(Task task) {
		taskRepository.save(task);
	}

	@Override
	public Set<ProjectTaskDetails> getProjectTaskDetails(long projectId) {
		return taskRepository.getProjectTaskDetails(projectId);
	}

	@Override
	public Set<TaskDto> getTypedTasksForProject(long projectId, String type, String status) {
		Set<Task> result;
		Set<Status> statuses = new HashSet<>();
		if (!type.equals("ALL")) {
			if (status == null) {
				statuses = statusService.getAll();
			} else if (status.toLowerCase().equals("open")) {
				statuses = statusService.findOpenStatuses();
			} else {
				statuses = statusService.findClosedStatuses();
			}
			result = taskRepository.findAllByProjectIdAndTypeNameAndStatusNameIn(projectId, type,
					statuses.stream().map(Status::getName).collect(Collectors.toSet()));
		} else {
			result = getAllTasksByProjectId(projectId);
		}
		return result.stream().map(CommonUtils::mapTaskToDto).collect(Collectors.toSet());
	}

}
