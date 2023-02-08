package com.javawwa25.app.springboot.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Person {

	private String email;
	private boolean isActive;
	private String activationCode;
	private String password;

	// mapping user with projects
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "users")
	private Set<Project> projects;

	@Transient // TEMP
	private Set<Task> tasks = new HashSet<>();

	@Builder
	public User(String firstName, String lastName, String email, boolean isActive, String activationCode,
			String password, Set<Project> projects, Set<Task> tasks) {
		super(firstName, lastName);
		this.email = email;
		this.isActive = isActive;
		this.activationCode = activationCode;
		this.password = password;
		this.projects = projects;
		this.tasks = tasks;
	}
	
	public void addProject(Project project) {
		if (this.projects == null) {
			this.projects = new HashSet<>();
		}
		this.projects.add(project);
	}
}
