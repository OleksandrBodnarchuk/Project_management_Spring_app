package com.javawwa25.app.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.UserService;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    // ADMIN ONLY
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@PathVariable(name = "user_id") long user_id, Model model) {
        User user = userService.getUserById(user_id);
        model.addAttribute("user", user);
        return "user/update_user";
    }

    // ADMIN
    @GetMapping("/{id}/deleteUser")
    public String deleteEmployee(@PathVariable(value = "id") long id) {
        this.userService.deleteUserById(id);
        return "redirect:/user/user-list";
    }

    // ADMIN
    @GetMapping("/user-list")
    public String showUserList(@PathVariable(name = "user_id") long user_id, Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "user/user-list";
    }

    // TEMP ADMIN ONLY
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        // save employee to database
//        userService.saveUser(user);
        return "redirect:/user/user-list";
    }
}
