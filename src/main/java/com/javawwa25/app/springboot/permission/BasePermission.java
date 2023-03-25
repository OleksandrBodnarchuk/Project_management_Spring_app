package com.javawwa25.app.springboot.permission;

import com.javawwa25.app.springboot.user.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePermission extends BaseEntity {

	private boolean canCreate;
	private boolean canRead;
	private boolean canUpdate;
	private boolean canDelete;
	
}
