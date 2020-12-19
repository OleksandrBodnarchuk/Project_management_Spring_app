package com.javawwa25.app.springboot.models;

import javax.persistence.*;

@Entity
@Table(name = "tasks", schema = "sda_final_app")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "task_name")
    private String name;
    @Column(name = "comments")
    private String comments;
    // How to connect it to Sprint?
    @Column(name = "sprint")
    private String sprint;
    // ENUM
    @Column(name = "priority")
    private PriorityTask priorityTask;
    // ENUM
    @Column(name = "progress")
    private Progress progress;

    // How to assign user? Mapping?
    @Column(name = "assigned")
    private String userAssigned;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public PriorityTask getPriorityTask() {
        return priorityTask;
    }

    public void setPriorityTask(PriorityTask priorityTask) {
        this.priorityTask = priorityTask;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public String getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }

    public Task(String name, String comments, String sprint, PriorityTask priorityTask, Progress progress, String userAssigned) {
        this.name = name;
        this.comments = comments;
        this.sprint = sprint;
        this.priorityTask = priorityTask;
        this.progress = progress;
        this.userAssigned = userAssigned;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comments='" + comments + '\'' +
                ", sprint='" + sprint + '\'' +
                ", priorityTask=" + priorityTask +
                ", progress=" + progress +
                ", userAssigned='" + userAssigned + '\'' +
                '}';
    }
}
