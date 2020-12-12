package com.javawwa25.app.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainPageController {

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("title", "Main Page");
        return "home-page";
    }

}
