package com.javawwa25.app.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String loginPage() {
		return "login-page";
	}

	@GetMapping
	public String userPage() {
		return "redirect:/user";
	}
}
