package com.javawwa25.app.springboot.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task extends BaseEntity {

	private String comments;

	@Column(name = "task_name")
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created")
	private Date created;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date task_startDate;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date task_endDate;

	@Column(name = "task_priority")
	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Column(name = "task_progress")
	@Enumerated(EnumType.STRING)
	private Progress progress;

	// Mapping Tasks with Project
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	// mapping tasks with user
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
