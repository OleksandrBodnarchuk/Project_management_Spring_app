package com.javawwa25.app.springboot.user.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDto {
	private long id;
	private String name;
	private Set<UserDto> users;
	private Set<ProjectDto> projects;
}
