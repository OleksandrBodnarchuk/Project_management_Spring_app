package com.javawwa25.app.springboot.user;

import java.util.HashSet;
import java.util.Set;

import com.javawwa25.app.springboot.account.Account;
import com.javawwa25.app.springboot.comment.Comment;
import com.javawwa25.app.springboot.group.UserGroup;
import com.javawwa25.app.springboot.project.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

	private String userStatus;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToMany(fetch = FetchType.LAZY, 
				cascade = { CascadeType.DETACH, 
							CascadeType.MERGE, 
							CascadeType.PERSIST,
							CascadeType.REFRESH }, 
				mappedBy = "users")
	private Set<Project> projects;
	
	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = { CascadeType.DETACH, 
						CascadeType.MERGE, 
						CascadeType.PERSIST,
						CascadeType.REFRESH }, 
			mappedBy = "users")
	private Set<UserGroup> groups;

	/*
	 * The best way to map a @OneToOne relationship is to use @MapsId. 
	 * This way, no even need a bidirectional association since you can always fetch the Account entity by using the User entity identifier.
	 * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
	 * */
	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@Builder
	public User(String firstName, String lastName, String userStatus, Account account, Set<Project> projects) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userStatus = userStatus;
		this.account = account;
		this.projects = projects;
	}

	public void addProject(Project project) {
		if (this.projects == null) {
			this.projects = new HashSet<>();
		}
		this.projects.add(project);
	}
}
