package com.javawwa25.app.springboot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDto {

	private long accountId;
	private String userName;
	
}
