package com.javawwa25.app.springboot.user.dto;

import java.util.Date;
import java.util.List;

import com.javawwa25.app.springboot.project.dto.ProjectDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
	private long accountId;
	private String status;
	@NotNull
    @NotEmpty
	private String firstName;
	@NotNull
    @NotEmpty
	private String lastName;
	@NotNull
    @NotEmpty
    @Email(message = "Email invalid")
	private String email;
	private boolean isAdmin;
	
	@Min(message = "Minimum 8 symbols", value = 8)
	private String password;
	private Date lastActiveDate;
	private Date registrationDate;
	private List<ProjectDto> projects;   
	private boolean generatePassword;

}