package com.javawwa25.app.springboot.services;

import com.javawwa25.app.springboot.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    List<Task> getAllTasks();

    void saveTask(Task task);

    Task getTaskById(long id);

    void deleteTaskById(long id);


}
