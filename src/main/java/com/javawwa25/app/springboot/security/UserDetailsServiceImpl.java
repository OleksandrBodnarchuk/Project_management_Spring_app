package com.javawwa25.app.springboot.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.debug(String.format("[%s] - %s - called", this.getClass().getSimpleName(), "loadUserByUsername"));
		 User user = userRepository.findByEmail(username);
	        if(user != null) {
	        	LOG.debug(String.format("[%s] - %s", this.getClass().getSimpleName(), "user loaded"));
	        	return new org.springframework.security.core.userdetails.User(
	        			user.getEmail(), 
	        			user.getPassword(),
	                    List.of(new SimpleGrantedAuthority("USER")));
	        } else {
	            throw new UsernameNotFoundException("Invalid username or password");
	        }
	}

}
