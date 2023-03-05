package com.javawwa25.app.springboot.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.javawwa25.app.springboot.account.Authority;
import com.javawwa25.app.springboot.user.User;

public class SecurityUtil {

	public static String getSessionUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			return currentUserName;
		}
		return null;
	}

	public static void updateSessionUser(User updatedUser) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUser.getAccount().getEmail(),
				updatedUser.getAccount().getPassword(), getAuthorities(updatedUser.getAccount().getAuthorities()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
		if (authorities != null && authorities.size() > 0) {
			return authorities.stream().map(Authority::getRole).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet());
		} else {
			return new HashSet<>();
		}
	}

}
