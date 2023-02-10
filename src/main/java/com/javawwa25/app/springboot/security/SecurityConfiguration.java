package com.javawwa25.app.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final UserDetailsService userDetailsService;
	
	public SecurityConfiguration(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		   http.csrf().disable()
           .authorizeHttpRequests()
					.requestMatchers(
							antMatcher("/h2-console/**"), 
							antMatcher("/login"), 
							antMatcher("/registration"),
							antMatcher("/css/**"), 
							antMatcher("/js/**"))
					.permitAll()
           .anyRequest().authenticated()
           .and()
           .formLogin(form -> form
                   .loginPage("/login")
                   .defaultSuccessUrl("/users")
                   .loginProcessingUrl("/login")
                   .failureUrl("/login?error=true")
                   .permitAll()
           ).logout(
                   logout -> logout
                           .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
           );
   
   http.headers().frameOptions().sameOrigin();

   return http.build();
	}

	private AntPathRequestMatcher antMatcher(String matcher) {
		return new AntPathRequestMatcher(matcher);
	}

	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}