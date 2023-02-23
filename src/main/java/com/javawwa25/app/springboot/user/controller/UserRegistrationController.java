package com.javawwa25.app.springboot.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.user.dto.UserRegistrationDto;
import com.javawwa25.app.springboot.user.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String getRegisterForm(Model model) {
		UserRegistrationDto user = new UserRegistrationDto();
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/save")
	public String register(@ModelAttribute("user") @Valid UserRegistrationDto user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "register";
		}
		userService.saveRegister(user);
		return "redirect:/login?registered";
	}
}
