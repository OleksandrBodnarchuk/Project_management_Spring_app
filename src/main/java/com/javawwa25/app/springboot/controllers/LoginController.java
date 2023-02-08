package com.javawwa25.app.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserLoginDto;

@Controller
public class LoginController {
	
	private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

	@GetMapping({ "", "/", "/login" })
    public String login(Model model) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET login - called");
		model.addAttribute("dto", new UserLoginDto());
        return "login-page";
    }
	
	@PostMapping({ "", "/", "/login" })
	public String loginUser(@ModelAttribute("dto") UserLoginDto dto) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - POST login - called");
		User user = userService.findByEmail(dto.getUsername());
		if (user != null && user.getPassword().equals(dto.getPassword())) {
			return "redirect:/user/" + user.getId();
		} else {
			return "login-page";
		}
	}

}
