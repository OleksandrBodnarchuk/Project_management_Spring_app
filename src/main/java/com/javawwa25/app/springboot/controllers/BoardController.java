package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.models.Task;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/project/{project_id}/board")
public class BoardController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @GetMapping()
    public String getBoard(@PathVariable(value = "project_id") long project_id,
                           Model model) {
        List<Task> taskList = taskService.getAllTasksByProjectId(project_id);
        model.addAttribute("taskList", taskList);
        model.addAttribute(taskService.getTaskById(project_id));
        return "/board/board1";
    }
}
