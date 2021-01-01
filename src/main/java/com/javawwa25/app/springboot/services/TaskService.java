package com.javawwa25.app.springboot.services;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    List<Task> getAllTasks();
    void saveTask(Task task);
    Task getTaskById(long id);
    void deleteTaskById(long id);
    Page<Task> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    List<Task> getAllTasksByProjectId(long project_id);
}
