package com.javawwa25.app.springboot.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
	public class SimpleUserDto {

	private long accountId;
	private String userName;
	private Boolean selected=false;
	
	public SimpleUserDto(long accountId, String userName) {
		this.accountId = accountId;
		this.userName = userName;
	}

	public SimpleUserDto(long accountId, String userName, Boolean selected) {
		this.accountId = accountId;
		this.userName = userName;
		this.selected = selected;
	}
	
}
