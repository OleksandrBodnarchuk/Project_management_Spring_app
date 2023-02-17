package com.javawwa25.app.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String error403() {
        return "/error/403";
    }
}
