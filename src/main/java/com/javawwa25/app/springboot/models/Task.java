package com.javawwa25.app.springboot.models;

import javax.persistence.*;

@Entity
@Table(name = "tasks", schema = "sda_final_app")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
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
    private String priorityTask;
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

    public String getPriorityTask() {
        return priorityTask;
    }

    public void setPriorityTask(String priorityTask) {
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
