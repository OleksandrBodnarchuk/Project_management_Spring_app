package com.javawwa25.app.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping
	public String loginPage() {
		return "login-page";
	}

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
	
}
