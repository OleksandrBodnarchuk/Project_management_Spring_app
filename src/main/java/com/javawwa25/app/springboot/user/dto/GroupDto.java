package com.javawwa25.app.springboot.user.dto;

import java.util.Set;

import com.javawwa25.app.springboot.security.validators.ValidGroupName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDto {
	private long id;
	@ValidGroupName
	private String name;
	private Set<SimpleUserDto> users;
	private Set<ProjectDto> projects;
}
