package com.javawwa25.app.springboot.services;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;


@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void saveProject(Project project) {
        this.projectRepository.save(project);
    }

    @Override
    public Project getProjectById(long id) {
        Optional<Project> optional = projectRepository.findById(id);
        Project project = null;
        if (optional.isPresent()) {
            project = optional.get();
        } else {
            throw new RuntimeException("Project not found for id :: " + id);
        }
        return project;
    }

    @Override
    public void deleteProjectById(long id) {
        this.projectRepository.deleteById(id);
    }


    @Override
    public List<Project> getAllProjectsByUserId(long user_id) {
        Query query = entityManager.createQuery(MessageFormat.format("SELECT u FROM Project u where user_id={0}", user_id));
        List<Project> projectList = query.getResultList();
        return projectList;
    }
}
