package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.services.ProjectService;
import com.javawwa25.app.springboot.services.TaskService;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    // Getting User details of current logged-in User
    @GetMapping()
    public String userPage(@CurrentSecurityContext(expression = "authentication.name") String userName) {
        User user = userRepository.findByEmail(userName);
        return "redirect:/user/" + user.getUser_id();
    }

    // MAIN PAGE FOR USER
    @GetMapping("/{user_id}")
    public String showUserPage(@PathVariable(value = "user_id") long user_id, Model model) {
        User user = userService.getUserById(user_id);
        List<Project> projectList = projectService.getAllProjectsByUserId(user_id);
        model.addAttribute("user", user);
        model.addAttribute("projectList", projectList);
        return "user/user-page";
    }

    // for admin only
    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/new_user";
    }


}
