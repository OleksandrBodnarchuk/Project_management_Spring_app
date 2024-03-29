package com.javawwa25.app.springboot.task.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.javawwa25.app.springboot.task.Priority;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskSaveDto {
	@Nullable
	private long id;
	private String name;
	private String description;
	private Priority priority;
	private String type;
	private String status;
	private long userAssigned;
	@Nullable
	private long userAdded;
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
