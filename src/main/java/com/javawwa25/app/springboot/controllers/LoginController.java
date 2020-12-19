package com.javawwa25.app.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("main/login")
    public String login(Model model){
        model.addAttribute("title","Login");
        return "login-page";
    }
}
