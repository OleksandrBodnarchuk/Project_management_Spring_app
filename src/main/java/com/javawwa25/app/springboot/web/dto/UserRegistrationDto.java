package com.javawwa25.app.springboot.web.dto;

import com.javawwa25.app.springboot.security.ValidEmail;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRegistrationDto {
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
	@NotNull
    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
    		message = "Minimum eight characters, at least one letter, one number and one special character")
    private String password;

}
