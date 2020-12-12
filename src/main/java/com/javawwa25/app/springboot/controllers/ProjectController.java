package com.javawwa25.app.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {

    @GetMapping("/project")
    public String mainPage(Model model){
        model.addAttribute("title", "Main Page");
        return "home-page";
    }
}
