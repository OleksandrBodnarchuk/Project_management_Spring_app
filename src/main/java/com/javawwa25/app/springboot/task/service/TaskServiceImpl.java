package com.javawwa25.app.springboot.task.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.project.service.ProjectService;
import com.javawwa25.app.springboot.task.Status;
import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.task.TaskType;
import com.javawwa25.app.springboot.task.dto.TaskDto;
import com.javawwa25.app.springboot.task.repo.TaskRepository;
import com.javawwa25.app.springboot.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements  TaskService{

   private final TaskRepository taskRepository;
   private final UserService userService;
   private final ProjectService projectService;
   private final TaskTypeService taskTypeService;
   private final StatusService statusService;

   @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void saveTask(TaskDto dto) {
    	Status status = statusService.findByName("NEW");
		TaskType taskType = taskTypeService.findByName(dto.getType());
		
		this.taskRepository.save(Task.builder()
    		.name(dto.getName())
    		.description(dto.getDescription())
    		.priority(dto.getPriority())
    		.taskType(taskType)
    		.status(status)
    		.startDate(dto.getStartDate())
    		.modificationDate(dto.getModificationDate())
    		.userAssigned(userService.getUserByAccountId(dto.getUserAssignedId()))
    		.userAdded(userService.getLoggedUser())
    		.endDate(dto.getEndDate())
    		.createdAt(dto.getCreatedAt())
    		.project(projectService.getProjectById(dto.getProjectId()))
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

}
