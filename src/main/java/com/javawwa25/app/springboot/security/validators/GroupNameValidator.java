package com.javawwa25.app.springboot.security.validators;

import com.javawwa25.app.springboot.group.service.GroupService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GroupNameValidator implements ConstraintValidator<ValidGroupName, String> {

	private final GroupService groupService;

	public GroupNameValidator(GroupService groupService) {
		this.groupService = groupService;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return groupService.findByName(value) == null;
	}

}
