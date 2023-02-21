package com.javawwa25.app.springboot.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Project extends Job {

	@Column(name = "project_name")
	private String name;

	@Column(name = "project_info")
	private String info;

	// mapping projects with user
	@ManyToMany(fetch = FetchType.EAGER, 
				cascade = { CascadeType.DETACH, 
							CascadeType.MERGE, 
							CascadeType.PERSIST,
							CascadeType.REFRESH })
	@JoinTable(name = "users_projects", 
			   joinColumns = @JoinColumn(name = "project_id"),
			   inverseJoinColumns = @JoinColumn(name = "users_id"))
	private Set<User> users;

	// mapping tasks with project
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
	private Set<Task> tasks;
	
	@Builder
	public Project(Date startDate, Date endDate, Date createdAt, Date modificationDate, String name, String info,
			Set<User> users, Set<Task> tasks) {
		super(startDate, endDate, createdAt, modificationDate);
		this.name = name;
		this.info = info;
		this.users = users;
		this.tasks = tasks;
	}
	
	public void addUser(User user) {
		if (this.users == null) {
			this.users = new HashSet<>();
		}
		this.users.add(user);
	}
	
}
