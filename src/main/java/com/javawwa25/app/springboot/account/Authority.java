package com.javawwa25.app.springboot.account;

import java.util.Set;

import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.Column;
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
	
	@Column(unique = true)
	private String role;
	
	@ManyToMany(mappedBy = "authorities")
	private Set<Account> accounts;
	
}
