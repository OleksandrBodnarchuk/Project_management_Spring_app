package com.javawwa25.app.springboot.web.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskDto {
	private long id;
	private String name;
	private String description;
	private String priority;
	private String type;
	private String status;
	private UserDto userAssigned;
	private UserDto userAdded;
	private Date startDate;
	private Date modificationDate;
	private Date endDate;
	private Date createdAt;

}
