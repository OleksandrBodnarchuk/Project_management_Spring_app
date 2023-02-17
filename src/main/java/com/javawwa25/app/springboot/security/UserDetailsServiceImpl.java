package com.javawwa25.app.springboot.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javawwa25.app.springboot.models.Account;
import com.javawwa25.app.springboot.models.Authority;
import com.javawwa25.app.springboot.services.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private final AccountService accountService;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.debug(String.format("[%s] - %s - called", this.getClass().getSimpleName(), "loadUserByUsername"));
		Account account = accountService.findByUsername(username).orElseThrow(() -> {
			return new UsernameNotFoundException("Invalid username or password");
		});
		LOG.debug(String.format("[%s] - %s", this.getClass().getSimpleName(), "user loaded"));
		return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(),
				getAuthorities(account.getAuthorities()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
		if (authorities != null && authorities.size() > 0) {
			return authorities.stream()
					.map(Authority::getRole)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet());
		} else {
			return new HashSet<>();
		}
	}
}
