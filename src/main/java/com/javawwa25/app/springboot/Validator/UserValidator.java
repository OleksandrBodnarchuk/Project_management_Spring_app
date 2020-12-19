package com.javawwa25.app.springboot.Validator;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.SecurityConfig;
import com.javawwa25.app.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private SecurityConfig.UserService userService;

    @Override
    public boolean supports(Class<?>aClass){
        return User.class.equals(aClass);
    }
    @Override
    public void validate(Object o, Errors errors){
        User user = (User)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username",
                "NotEmpty");
        if (user.getUsername().length()<6 || user.getUsername().length()>80){
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername())!=null){
            errors.rejectValue("username", "Duplicate.userForm.username");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password",
                "NotEmpty ");
        if (user.getPassword().length()<8 || user.getPassword().length()>80){
            errors.rejectValue("password", "Size.userForm.password");
        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm",
                    "Diff.userForm.passwordConfirm");
        }
    }
}
