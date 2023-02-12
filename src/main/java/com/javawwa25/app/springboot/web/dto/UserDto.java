package com.javawwa25.app.springboot.web.dto;

import com.javawwa25.app.springboot.security.validators.ValidEmail;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    @ValidEmail(message = "Email invalid or exists")
	private String email;
}