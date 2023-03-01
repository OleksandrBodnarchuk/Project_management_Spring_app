package com.javawwa25.app.springboot.group;

import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter 
@Setter
public class UserGroup extends BaseEntity{
	
	private String name;

}
