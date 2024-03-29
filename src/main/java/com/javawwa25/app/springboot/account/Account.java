package com.javawwa25.app.springboot.account;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.javawwa25.app.springboot.file.FileData;
import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
	@JoinTable(name = "account_role", joinColumns = {
			@JoinColumn(name = "account_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Set<Role> roles;
	
	@Column(nullable = false)
	private Boolean isAdmin;
	
	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "photo_id")
	private FileData photo;
	// private Boolean activated;
	// private LocalDate activationDate;
	
}
