package com.javawwa25.app.springboot.web.dto;

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
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
