package com.javawwa25.app.springboot.utils;
import java.util.stream.Collectors;

import com.javawwa25.app.springboot.group.UserGroup;
import com.javawwa25.app.springboot.project.Project;
import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.task.dto.TaskDto;
import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.dto.GroupDto;
import com.javawwa25.app.springboot.user.dto.ProjectDto;
import com.javawwa25.app.springboot.user.dto.SimpleUserDto;

public class CommonUtils {

	public static SimpleUserDto createSimpleUserDto(User user) {
		return new SimpleUserDto(user.getAccount().getAccountId(),
				user.getFirstName() + " " + user.getLastName());
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

	public static UserGroup mapDtoToGroup(GroupDto dto) {
		UserGroup group = new UserGroup();
		group.setName(dto.getName());
		return group;
	}
	
	public static GroupDto mapGroupToDto(UserGroup group) {
		GroupDto dto = new GroupDto();
		dto.setId(group.getId());
		dto.setName(group.getName());
		dto.setProjects(group.getProjects().stream().map(CommonUtils::mapProjectToDto).collect(Collectors.toSet()));	
		dto.setUsers(group.getUsers().stream().map(CommonUtils::createSimpleUserDto).collect(Collectors.toSet()));
		return dto;
	}
	
	public static ProjectDto mapProjectToDto(Project project) {
		ProjectDto dto = new ProjectDto();
		dto.setId(project.getId());
		dto.setName(project.getName());
		return dto;
	}

}
