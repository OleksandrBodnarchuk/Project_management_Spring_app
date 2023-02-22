package com.javawwa25.app.springboot.web.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.javawwa25.app.springboot.models.Priority;
import com.javawwa25.app.springboot.models.Type;

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
	private UserDto userAssigned;
	private UserDto userAdded;
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
	@Nullable
	private long projectId;

}
