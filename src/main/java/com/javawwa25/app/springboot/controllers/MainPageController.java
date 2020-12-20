package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.models.Employee;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;


@Controller
public class MainPageController {

    @Autowired
    EmployeeService employeeService;

    public MainPageController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        Employee employee = new Employee();
        Project project = new Project();
        model.addAttribute("employee", employee);
        model.addAttribute("project", project);
        return "home-page";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "registration-form";
    }


}
