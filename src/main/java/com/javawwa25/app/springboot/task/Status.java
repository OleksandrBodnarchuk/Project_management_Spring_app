package com.javawwa25.app.springboot.task;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status extends BaseEntity {

	@Column(unique = true)
	private String name;

	@Min(value = 0) // 0 = New
	private Integer priority;

	@ManyToMany(mappedBy = "statuses")
	private Set<TaskType> taskTypes;

	public Status(String name, @Min(0) Integer priority) {
		this.name = name;
		this.priority = priority;
	}

	public void addTaskType(TaskType type) {
		if (Objects.isNull(this.taskTypes)) {
			this.taskTypes = new HashSet<>();
		}
		this.taskTypes.add(type);
	}
}
