package com.javawwa25.app.springboot.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority extends BaseEntity {

	private String role;
	
	@ManyToMany(mappedBy = "authorities")
	private Set<Account> accounts;
	
}