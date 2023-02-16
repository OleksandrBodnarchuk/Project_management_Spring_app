package com.javawwa25.app.springboot.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.TaskRepository;
import com.javawwa25.app.springboot.web.dto.UserDto;

@Service
public class TaskServiceImpl implements  TaskService{

   private final TaskRepository taskRepository;
   private final UserService userService;

	public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
		this.taskRepository = taskRepository;
		this.userService = userService;
	}

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
	public void fillUserPageDtoModel(long userId, Model model) {
		Set<Task> assignedTasks = getAssignedTasksByUserId(userId);
		Set<Task> createdTasks = getCreatedTasksByUserId(userId);
		
		User user = userService.getUserById(userId);
		UserDto dto = new UserDto();
		dto.setAccountId(user.getAccount().getAccountId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getAccount().getEmail());
		
		model.addAttribute("createdTasks", createdTasks);
		model.addAttribute("assignedTasks", assignedTasks);
        model.addAttribute("user", user);
	}

	private Set<Task> getCreatedTasksByUserId(long userId) {
		return taskRepository.findAllByUserAddedId(userId);
	}

	private Set<Task> getAssignedTasksByUserId(long userId) {
		return taskRepository.findAllByUserAssignedId(userId);
		
	}


}
