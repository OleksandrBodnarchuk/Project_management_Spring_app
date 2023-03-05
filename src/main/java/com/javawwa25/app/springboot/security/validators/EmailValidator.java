package com.javawwa25.app.springboot.security.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.javawwa25.app.springboot.account.service.AccountService;
import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

	private final UserService userService;
	private final AccountService accountService;
	

	@Override
	public void initialize(ValidEmail constraintAnnotation) {
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return validateEmail(email) ? validateEmailExist(email) : false;
	}

	private boolean validateEmailExist(String email) {
		User loggedUser = userService.getLoggedUser();
		User userToUpdate = userService.findByEmail(email);
		// own profile
		if(userToUpdate == null || loggedUser.equals(userToUpdate)) {
			return true;
		} else { // else profile
			return accountService.findEmail(email) == 0;
		}
	}

	private boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}