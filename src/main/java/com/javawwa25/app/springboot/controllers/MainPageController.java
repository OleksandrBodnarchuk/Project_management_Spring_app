package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.models.Employee;
import com.javawwa25.app.springboot.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainPageController {

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("title", "Main Page");
        return "home-page";

}
}
