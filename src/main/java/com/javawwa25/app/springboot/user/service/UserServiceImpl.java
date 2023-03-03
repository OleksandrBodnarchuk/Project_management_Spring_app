package com.javawwa25.app.springboot.user.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javawwa25.app.springboot.account.Account;
import com.javawwa25.app.springboot.account.Authority;
import com.javawwa25.app.springboot.account.repo.AuthorityRepository;
import com.javawwa25.app.springboot.account.service.AccountService;
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
	public User save(User dto) {
		return userRepository.save(dto);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByAccountEmail(email);
	}

	@Transactional
	@Override
	public User saveRegister(UserRegistrationDto dto) {
		Account account = accountService.save(Account.builder()
								.authority(setAuthority(dto.isAdmin()))
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
	public void update(UserDto dto) {
		User user = getLoggedUser();
		if(user.getAccount().getAccountId() != dto.getAccountId()) {
			throw new RuntimeException("Id not match user id");
		} else {
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.getAccount().setEmail(dto.getEmail());
			this.save(user);
		}
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
	public User save(UserDto dto) {
		Account account = accountService.save(
				Account.builder()
				.authority(setAuthority(dto.isAdmin()))
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

	private Authority setAuthority(boolean admin) {
		return admin ? 
				authorityRepository.save(Authority.builder().role("ADMIN").build()) : 
					authorityRepository.save(Authority.builder().role("USER").build());
	}

	private void setUserDtoInfoDetails(User user, UserDto dto) {
		dto.setAccountId(user.getAccount().getAccountId());
		dto.setEmail(user.getAccount().getEmail());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setAdmin(user.getAccount().getAuthorities().stream()
				.anyMatch(authority -> authority.getRole().equals("ADMIN")));
		dto.setLastActiveDate(user.getAccount().getLastActiveDate());
		dto.setRegistrationDate(user.getAccount().getRegistrationDate());
	}

	@Secured("ADMIN")
	@Override
	public void updateUser(UserDto dto) {
		User user = userRepository.findByAccountAccountId(Long.valueOf(dto.getAccountId()));
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.getAccount().setEmail(dto.getEmail());
		if(dto.isGeneratePassword()) {
			// TODO: generate new password
		}
		userRepository.save(user);
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
}
