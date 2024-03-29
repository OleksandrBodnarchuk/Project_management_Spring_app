package com.javawwa25.app.springboot.user.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javawwa25.app.springboot.account.Account;
import com.javawwa25.app.springboot.account.Role;
import com.javawwa25.app.springboot.account.repo.AuthorityRepository;
import com.javawwa25.app.springboot.account.service.AccountService;
import com.javawwa25.app.springboot.file.FileData;
import com.javawwa25.app.springboot.project.dto.ProjectDto;
import com.javawwa25.app.springboot.security.SecurityUtil;
import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.dto.GroupDto;
import com.javawwa25.app.springboot.user.dto.SimpleUserDto;
import com.javawwa25.app.springboot.user.dto.UserDto;
import com.javawwa25.app.springboot.user.dto.UserRegistrationDto;
import com.javawwa25.app.springboot.user.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AccountService accountService;
	private final AuthorityRepository authorityRepository;

	@Override
	public List<UserDto> getAllUserDtos() {
		return userRepository.findAll().stream()
				.map(user -> {
					UserDto dto = UserDto.builder().build();
					setUserDtoInfoDetails(user, dto);
					return dto;
					})
				.collect(Collectors.toList());
	}

	@Override
	public User getUserById(long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByAccountEmail(email);
	}

	@Transactional
	@Override
	public User saveRegister(UserRegistrationDto dto) {
		Account account = accountService.save(Account.builder()
								.role(setAuthority(dto.isAdmin()))
								.email(dto.getEmail())
								.password(passwordEncoder.encode(dto.getPassword()))
								.registrationDate(new Date())
								.build());
		return userRepository.save(User.builder()
				.firstName(dto.getFirstName())		
				.lastName(dto.getLastName())
				.account(account)
				.build());
	}

	@Override
	public User getLoggedUser() {
		return findByEmail(SecurityUtil.getSessionUser());
	}

	@Override
	public void userLogged() {
		User user = getLoggedUser();
		user.getAccount().setLastActiveDate(new Date());
		this.save(user);
	}

	@Override
	@Transactional
	public void update(UserDto dto) {
		User loggedUser = getLoggedUser();
		if (dto.getAccountId() != loggedUser.getAccount().getAccountId()) {
			adminUpdateUser(dto);
		} else {
			updateUserDetails(dto, loggedUser);
			this.updateSessionUser(loggedUser);
		}
	}

	@Secured({ "ADMIN" })
	@Transactional
	private void adminUpdateUser(UserDto dto) {
		User otherUser = getUserByAccountId(dto.getAccountId());
		updateUserDetails(dto, otherUser);
	}

	private void updateUserDetails(UserDto dto, User user) {
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.getAccount().setEmail(dto.getEmail());
		user.setUserStatus(dto.getStatus());
		userRepository.save(user);
	}

	@Override
	public UserDto getLoggedUserDto() {
		return setUserDetailsDto(getLoggedUser());

	}
	
	@Override
	public UserDto getUserDtoByAccountId(long id) {
		User user = userRepository.findByAccountAccountId(id);
		UserDto dto = setUserDetailsDto(user);
		return dto;
	}
	
	private UserDto setUserDetailsDto(User user) {
		UserDto dto = UserDto.builder().build();
		setUserDtoInfoDetails(user, dto);
		dto.setProjects(user.getProjects().stream()
				.map(project -> {
			return ProjectDto.builder()
					.id(project.getId())
					.name(project.getName())
					.build();
		}).collect(Collectors.toList()));
		return dto;
	}

	@Override
	@Transactional
	public User save(UserDto dto) {
		Account account = accountService.save(
				Account.builder()
				.role(setAuthority(dto.getIsAdmin()))
				.email(dto.getEmail())
				.password(passwordEncoder.encode(dto.getPassword()))
				.registrationDate(new Date())
				.build());
		User user = User.builder()
						.firstName(dto.getFirstName())		
						.lastName(dto.getLastName())
						.account(account)
						.build();
		return userRepository.save(user);
	}

	private Role setAuthority(boolean admin) {
		return admin ? authorityRepository.findByName("ADMIN") : authorityRepository.findByName("USER");
	}

	private void setUserDtoInfoDetails(User user, UserDto dto) {
		dto.setAccountId(user.getAccount().getAccountId());
		dto.setEmail(user.getAccount().getEmail());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setStatus(user.getUserStatus());
		dto.setIsAdmin(user.getAccount().getRoles().stream()
				.anyMatch(role -> role.getName().equals("ADMIN")));
		dto.setLastActiveDate(user.getAccount().getLastActiveDate());
		dto.setRegistrationDate(user.getAccount().getRegistrationDate());
	}

	@Secured("ADMIN")
	@Transactional
	@Override
	public void updateUser(UserDto dto) {
		User user = userRepository.findByAccountAccountId(Long.valueOf(dto.getAccountId()));
		updateUserDetails(dto, user);
		if(dto.getGeneratePassword()) {
			// TODO: generate new password
		}
		this.updateSessionUser(userRepository.save(user));
	}

	@Override
	public User getUserByAccountId(long id) {
		return userRepository.findByAccountAccountId(id);
	}
	
	@Override
	public List<SimpleUserDto> getSimpleDtos() {
		return userRepository.getUserDtoName();
	}

	@Override
	public List<SimpleUserDto> getAllUsersForGroup(GroupDto group) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - GET getAllUsersForGroup - called");
		Set<SimpleUserDto> userDtos = group.getUsers();
		if (userDtos != null && !userDtos.isEmpty()) {
			Set<Long> accountIds = userDtos.stream().map(SimpleUserDto::getAccountId).collect(Collectors.toSet());
			return userRepository.findByAccountAccountIdIn(accountIds);
		} else {
			return userRepository.getUserDtoName();
		}
	}
	
	private void updateSessionUser(User updatedUser) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUser.getAccount().getEmail(),
				updatedUser.getAccount().getPassword(), getAuthorities(updatedUser.getAccount().getRoles()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> authorities) {
		if (authorities != null && authorities.size() > 0) {
			return authorities.stream().map(Role::getName).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet());
		} else {
			return new HashSet<>();
		}
	}

	@Override
	public void updateAvatar(FileData fileData) {
		accountService.updateAvatar(getLoggedUser().getAccount(),fileData);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
