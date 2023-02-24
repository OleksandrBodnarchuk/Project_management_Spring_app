package com.javawwa25.app.springboot.utils;

import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.task.dto.TaskDto;
import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.dto.SimpleUserDto;

public class CommonUtils {

	public static SimpleUserDto createSimpleUserDto(User userAssigned) {
		return new SimpleUserDto(userAssigned.getAccount().getAccountId(),
				userAssigned.getFirstName() + " " + userAssigned.getLastName());
	}

	public static TaskDto mapTaskToDto(Task task) {
		return TaskDto.builder()
				.id(task.getId())
				.name(task.getName())
				.description(task.getDescription())
				.priority(task.getPriority())
				.type(task.getType().getName())
				.status(task.getStatus().getName())
				.userAssigned(CommonUtils.createSimpleUserDto(task.getUserAssigned()))
				.userAdded(CommonUtils.createSimpleUserDto(task.getUserAdded()))
				.modificationDate(task.getModificationDate())
				.build();
	}

}
