package com.javawwa25.app.springboot.task;

import java.util.HashSet;
import java.util.Set;

import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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
	
	@Enumerated(EnumType.STRING)
	private Type name;
	
	@OneToMany(mappedBy = "taskType", 
			   cascade = { CascadeType.DETACH,
	        		   	   CascadeType.MERGE,
	        		   	   CascadeType.PERSIST,
	        		   	   CascadeType.REFRESH })
	private Set<Status> statuses;
	
	public void addStatuse(Status status) {
		if (this.statuses == null) {
			this.statuses = new HashSet<>();
		}
		this.statuses.add(status);
	}
}
