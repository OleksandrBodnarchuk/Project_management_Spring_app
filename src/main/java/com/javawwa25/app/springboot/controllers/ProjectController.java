package com.javawwa25.app.springboot.controllers;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.services.EmployeeService;
import com.javawwa25.app.springboot.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    // display list of employees
    @GetMapping("/project")
    public String viewHomePage(Model model) {
        return findPaginated(1, "projectName", "asc", model);
    }

    @GetMapping("/project/showNewProjectForm")
    public String showNewProjectForm(Model model) {
        // create model attribute to bind form data
        Project project = new Project();
        model.addAttribute("project", project);
        return "new_project";
    }

    @PostMapping("/project/saveProject")
    public String saveProject(@ModelAttribute("project") Project project) {
        project.getAssigned();
        projectService.saveProject(project);


        return "redirect:/project";   // CHECK REDIRECT !!!!!!!!!!!!
    }
    /**
     * Another save project() needs to be tested
     *
     * !@PostMapping(value = "/saveProject")
     * public String saveProject(@ModelAttribute Project project, Model model) {
     *     // Fill id field for project.rolesNeeded
     *     mRoleService.setRolesId(project.getRolesNeeded());
     *     project.fixCollaboratorsAndRoles();
     *
     *     mProjectService.save(project);
     *     return "redirect:/";
     */


    @GetMapping("/project/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {

        // get employee from the service
        Project project = projectService.getProjectById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("project", project);
        return "update_project";
    }

    @GetMapping("/project/deleteProject/{id}")
    public String deleteProject(@PathVariable (value = "id") long id) {

        // call delete employee method
        this.projectService.deleteProjectById(id);
        return "redirect:/project";   // CHECK REDIRECT !!!!!!!!!!!!
    }


    @GetMapping("/project/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Project> page = projectService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Project> projectList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("projectList", projectList);
        return "project-list";
    }
}
