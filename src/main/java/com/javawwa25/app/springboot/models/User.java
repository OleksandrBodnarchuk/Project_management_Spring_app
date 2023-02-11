package com.javawwa25.app.springboot.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class User extends BaseEntity {

	@Column(name = "status")
	private String status;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	// mapping user with projects
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "users")
	private Set<Project> projects;

	@Transient // TEMP
	private Set<Task> tasks = new HashSet<>();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "account")
	private Account account;

	@Builder
	public User(String firstName, String lastName, String status, Account account, Set<Project> projects, Set<Task> tasks) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.account = account;
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
