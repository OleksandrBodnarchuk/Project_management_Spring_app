package com.javawwa25.app.springboot.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "project", schema = "sda_final_app")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name="assignee")
    private String assigned;

    @Column(name="project_info")
    private String projectInfo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "current_task")
    private String current_task;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCurrent_task() {
        return current_task;
    }

    public void setCurrent_task(String current_task) {
        this.current_task = current_task;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public String getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", current_task='" + current_task + '\'' +
                ", assigned='" + assigned + '\'' +
                '}';
    }
}
