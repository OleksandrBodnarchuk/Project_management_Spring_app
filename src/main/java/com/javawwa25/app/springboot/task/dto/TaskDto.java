package com.javawwa25.app.springboot.task.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.javawwa25.app.springboot.task.Priority;
import com.javawwa25.app.springboot.task.Type;
import com.javawwa25.app.springboot.user.dto.SimpleUserDto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskDto {
	@Nullable
	private long id;
	private String name;
	private String description;
	private Priority priority;
	private Type type;
	private String status;
	private SimpleUserDto userAssigned;
	private long userAssignedId;
	@Nullable
	private SimpleUserDto userAdded;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nullable
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nullable
	private Date modificationDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nullable
	private Date endDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nullable
	private Date createdAt;
	@Nullable
	private String estimated;
	private long projectId;

}
