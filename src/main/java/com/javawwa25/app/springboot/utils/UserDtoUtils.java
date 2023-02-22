package com.javawwa25.app.springboot.utils;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.web.dto.SimpleUserDto;

public class UserDtoUtils {

	public static SimpleUserDto createSimpleUserDto(User userAssigned) {
		return new SimpleUserDto(userAssigned.getAccount().getAccountId(),
				userAssigned.getFirstName() + " " + userAssigned.getLastName());
	}
}
