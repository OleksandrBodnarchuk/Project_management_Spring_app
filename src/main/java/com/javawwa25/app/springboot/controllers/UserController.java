package com.javawwa25.app.springboot.controllers;


import com.javawwa25.app.springboot.Validator.UserValidator;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.SecurityConfig;
import com.javawwa25.app.springboot.services.SecurityService;
import com.javawwa25.app.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private SecurityConfig.UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator validator;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new User());
        return "blocks/RegisterButton";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userForm") User userForm,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "blocks/RegisterButton";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(),
                userForm.getPasswordConfirm());
        return "redirect:welcome";

    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("message", "You have been loged out successfully");
        return "blocks/login-button";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        return "home-page";
    }
}
