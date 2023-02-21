package com.javawwa25.app.springboot.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.repositories.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements  TaskService{

   private final TaskRepository taskRepository;
   private final UserService userService;
   
	@Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void saveTask(Task task) {
        this.taskRepository.save(task);
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


}
