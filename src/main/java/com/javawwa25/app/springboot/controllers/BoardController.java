package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.services.EmployeeService;
import com.javawwa25.app.springboot.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    TaskService taskService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/board")
    public String getBoard(){
        return "/board/board";
    }
}
