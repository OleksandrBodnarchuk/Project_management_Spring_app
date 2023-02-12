package com.javawwa25.app.springboot.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
	private LocalDate startDate; // creation date

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate endDate; // close date
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate createdAt;

}
