package com.javawwa25.app.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.services.UserService;


@Controller
public class MainPageController {


    final
    UserService userService;

    public MainPageController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String afterLoginRedirect() {
        return "redirect:/user";
    }

    // SAVE USER --> for admin to manipulate users later
    @PostMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute("user") User user) {
        // save user to database
        userService.saveUser(user);
        return "redirect:/user/" + user.getUser_id();
    }


}
