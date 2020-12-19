package com.javawwa25.app.springboot.services;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.RoleRepository;
import com.javawwa25.app.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.HashSet;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

@Qualifier("userDetailsServiceImpl")
@Autowired
private UserDetailsService userDetailsService;

@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
}



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/main","/register")
                .permitAll()
                .anyRequest().authenticated().and().formLogin()
                .loginPage("/login").permitAll().and().logout().permitAll();
    }
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    public static interface UserService {
        void save(User user);

        User findByUsername(String username);
    }

    @Service
    public static class UserServiceImpl implements UserService {
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private RoleRepository roleRepository;
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Override
        public void save(User user) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(new HashSet<>(roleRepository.findAll()));
            userRepository.save(user);
        }

        @Override
        public User findByUsername(String username) {
            return userRepository.findByUsername(username);
        }
    }

    @Component
    public static class UserValidator implements Validator {

        @Autowired
        private UserService userService;

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
}
