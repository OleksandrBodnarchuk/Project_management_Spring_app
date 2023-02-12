package com.javawwa25.app.springboot.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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

	private String name;
	
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
