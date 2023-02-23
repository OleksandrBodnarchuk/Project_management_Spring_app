package com.javawwa25.app.springboot.task;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job extends BaseEntity {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate; // creation date

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate; // close date
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modificationDate;

}
