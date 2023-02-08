package com.javawwa25.app.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Task;

@Service
public interface TaskService {
    List<Task> getAllTasks();

    void saveTask(Task task);

    Task getTaskById(long id);

    void deleteTaskById(long id);


}