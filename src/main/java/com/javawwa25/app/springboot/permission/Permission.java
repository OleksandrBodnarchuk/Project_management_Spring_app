package com.javawwa25.app.springboot.permission;

import java.util.Set;

import com.javawwa25.app.springboot.account.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BasePermission {
	private String name;
	
	@ManyToMany(mappedBy = "permissions")
	private Set<Role> authorities;
}
