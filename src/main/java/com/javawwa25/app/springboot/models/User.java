package com.javawwa25.app.springboot.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

	// mapping user with roles
//	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
//			CascadeType.REFRESH })
//	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//	private Set<Role> roles = new HashSet<>();

	// mapping user with projects
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "user")
	private Set<Project> projects = new HashSet<>();;

	// mapping user with tasks
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "user")
	private Set<Task> tasks = new HashSet<>();

	@Builder
	public User(Long id, String firstName, String lastName, String email, boolean isActive, String activationCode,
			String password, Set<Project> projects, Set<Task> tasks) {
		super(id, firstName, lastName);
		this.email = email;
		this.isActive = isActive;
		this.activationCode = activationCode;
		this.password = password;
		this.projects = projects;
		this.tasks = tasks;
	}

}
