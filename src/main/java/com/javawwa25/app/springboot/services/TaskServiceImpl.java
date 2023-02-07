package com.javawwa25.app.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.repositories.ProjectRepository;
import com.javawwa25.app.springboot.repositories.TaskRepository;

@Service
public class TaskServiceImpl implements  TaskService{


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;


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




}
