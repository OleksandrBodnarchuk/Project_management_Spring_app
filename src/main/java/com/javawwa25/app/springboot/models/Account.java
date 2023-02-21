package com.javawwa25.app.springboot.models;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

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
public class Account extends BaseEntity {

	@Column(name = "email", unique = true)
	private String email;
	private Long accountId;
	private String password;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registrationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastActiveDate;

	@Singular
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "account_authority", joinColumns = {
			@JoinColumn(name = "account_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_id", referencedColumnName = "id") })
	private Set<Authority> authorities;

	// private Boolean activated;
	// private LocalDate activationDate;
	
}
