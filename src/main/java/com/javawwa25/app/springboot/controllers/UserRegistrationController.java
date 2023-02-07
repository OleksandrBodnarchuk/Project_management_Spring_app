package com.javawwa25.app.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    // constructor dependency injection
    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    // get mapping for Registration Page Form
    @GetMapping
    public String showRegistrationPage(Model model) {
        // --> new object that is used in form by Thymeleaf
        model.addAttribute("user", new UserRegistrationDto());
        return "registration-form";
    }

    // handler method for POST request
    // binding user with RegistrationDTO
    @PostMapping
    public String registerNewUser(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        userService.save(new User());
        return "redirect:/registration?success";
    }
}
