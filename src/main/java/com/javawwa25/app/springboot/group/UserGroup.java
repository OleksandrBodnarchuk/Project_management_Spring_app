package com.javawwa25.app.springboot.group;

import java.util.Set;

import com.javawwa25.app.springboot.project.Project;
import com.javawwa25.app.springboot.user.BaseEntity;
import com.javawwa25.app.springboot.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter 
@Setter
@Builder
public class UserGroup extends BaseEntity{
	
	private String name;
	
	@Singular
	@ManyToMany(fetch = FetchType.EAGER, 
			cascade = { CascadeType.DETACH, 
						CascadeType.MERGE, 
						CascadeType.PERSIST,
						CascadeType.REFRESH })
	@JoinTable(name = "users_groups", 
	   joinColumns = @JoinColumn(name = "group_id"),
	   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;
	
	@Singular
	@ManyToMany(fetch = FetchType.EAGER, 
			cascade = { CascadeType.DETACH, 
						CascadeType.MERGE, 
						CascadeType.PERSIST,
						CascadeType.REFRESH })
	@JoinTable(name = "groups_projects", 
	   joinColumns = @JoinColumn(name = "group_id"),
	   inverseJoinColumns = @JoinColumn(name = "project_id"))
	private Set<Project> projects;

}
