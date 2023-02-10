package com.javawwa25.app.springboot.security.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.security.ValidEmail;
import com.javawwa25.app.springboot.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

	private final UserService userService;
	
	public EmailValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void initialize(ValidEmail constraintAnnotation) {
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return validateEmail(email) ? validateEmailExist(email) : false;
	}

	private boolean validateEmailExist(String email) {
		User existingUser = userService.findByEmail(email);
		if (existingUser != null && StringUtils.hasLength(email)) {
			return false;
		}
		return true;
	}

	private boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}