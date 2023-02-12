package com.javawwa25.app.springboot.models;

import jakarta.persistence.Entity;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status extends BaseEntity {

	private String name;
	
	@ManyToOne
	@JoinColumn(name = "task_type")
	private TaskType taskType;
	
}
