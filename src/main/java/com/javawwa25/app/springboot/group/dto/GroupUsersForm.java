package com.javawwa25.app.springboot.group.dto;

import java.util.ArrayList;
import java.util.List;

import com.javawwa25.app.springboot.user.dto.SimpleUserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupUsersForm {
	private List<SimpleUserDto> users = new ArrayList<>();
	
	
}
