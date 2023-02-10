package com.javawwa25.app.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

@Controller
public class LoginController {
	private UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login-page";
	}

	@GetMapping("/register")
	public String getRegisterForm(Model model) {
		UserRegistrationDto user = new UserRegistrationDto();
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/register/save")
	public String register(@Validated @ModelAttribute("user") UserRegistrationDto user, BindingResult result, Model model) {
		User existingUserEmail = userService.findByEmail(user.getEmail());
		if (existingUserEmail != null && existingUserEmail.getEmail() != null
				&& !existingUserEmail.getEmail().isEmpty()) {
			return "redirect:/register?fail";
		}
		User existingUserUsername = userService.findByEmail(user.getEmail());
		if (existingUserUsername != null && existingUserUsername.getEmail() != null
				&& !existingUserUsername.getEmail().isEmpty()) {
			return "redirect:/register?fail";
		}
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "register";
		}
		userService.saveRegister(user);
		return "redirect:/clubs?success";
	}
}
