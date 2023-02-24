package com.javawwa25.app.springboot.task;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class TaskType extends BaseEntity {

	private String name;

	@ManyToMany
	@JoinTable(name = "type_status", 
			joinColumns = { @JoinColumn(name = "type") }, 
			inverseJoinColumns = {
			@JoinColumn(name = "status") })
	private Set<Status> statuses;

	public void addStatus(Status status) {
		if (Objects.isNull(this.statuses)) {
			this.statuses = new HashSet<>();
		}
		this.statuses.add(status);
		status.addTaskType(this);
	}
}
