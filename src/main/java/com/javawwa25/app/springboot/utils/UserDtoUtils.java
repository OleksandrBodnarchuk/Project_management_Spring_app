package com.javawwa25.app.springboot.utils;

import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.dto.SimpleUserDto;

public class UserDtoUtils {

	public static SimpleUserDto createSimpleUserDto(User userAssigned) {
		return new SimpleUserDto(userAssigned.getAccount().getAccountId(),
				userAssigned.getFirstName() + " " + userAssigned.getLastName());
	}
}
