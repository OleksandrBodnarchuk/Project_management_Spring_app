package com.javawwa25.app.springboot.models;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Builder
public class Account extends BaseEntity {

	@Column(name = "email", unique = true)
	private String email;
	private Long accountId;
	private String password;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate registrationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastActiveDate;
	
	 private Boolean isAdmin;
	// private Boolean activated;
	// private LocalDate activationDate;
	
}
