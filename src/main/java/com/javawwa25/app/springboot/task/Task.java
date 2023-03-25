package com.javawwa25.app.springboot.task;

import java.util.Date;
import java.util.Set;

import com.javawwa25.app.springboot.comment.Comment;
import com.javawwa25.app.springboot.project.Project;
import com.javawwa25.app.springboot.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task extends Job {

	private String name;
	@Lob
	private String description;

	@Column(name = "task_priority")
	@Enumerated(EnumType.STRING)
	private Priority priority;

	@OneToOne
	@JoinColumn(name = "type_id")
	private TaskType type;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "user_assigned_id")
	private User userAssigned;

	@ManyToOne
	@JoinColumn(name = "user_added_id")
	private User userAdded;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments;

	@Builder
	public Task(Date startDate, Date endDate, Date createdAt, Date modificationDate, String name, String description,
			Priority priority, TaskType taskType, Status status, Project project, User userAssigned, User userAdded) {
		super(startDate, endDate, createdAt, modificationDate);
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.type = taskType;
		this.status = status;
		this.project = project;
		this.userAssigned = userAssigned;
		this.userAdded = userAdded;
	}

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setTask(this);
    }
 
    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setTask(null);
    }

}
