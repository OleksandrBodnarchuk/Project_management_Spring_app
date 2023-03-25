package com.javawwa25.app.springboot.account;

import java.util.Set;

import com.javawwa25.app.springboot.permission.Permission;
import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority extends BaseEntity {
	
	@Column(unique = true)
	private String role;
	
	@ManyToMany(mappedBy = "authorities")
	private Set<Account> accounts;
	
	@Singular
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "authority_permission", joinColumns = {
			@JoinColumn(name = "permission_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_id", referencedColumnName = "id") })
	private Set<Permission> permissions;
	
}
