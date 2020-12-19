package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.services.EmployeeService;
import com.javawwa25.app.springboot.models.Employee;
import com.javawwa25.app.springboot.services.SecurityConfig;
import com.javawwa25.app.springboot.services.SecurityService;
import com.javawwa25.app.springboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeControler {

	@Autowired
	private EmployeeService employeeService;
	
	// display list of employees
	@GetMapping("/user")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		employeeService.saveEmployee(employee);
		return "redirect:/user";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/user";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "user-list";
	}

    @Controller
    public static class UserController {
        @Autowired
        private SecurityConfig.UserService userService;

        @Autowired
        private SecurityService securityService;

        @Autowired
        private SecurityConfig.UserValidator validator;

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

	@Controller
	public static class LoginController {

		@GetMapping("main/login")
		public String login(Model model){
			model.addAttribute("title","Login");
			return "login-page";
		}
	}
}
