package com.javawwa25.app.springboot.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Account;
import com.javawwa25.app.springboot.models.Authority;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.AuthorityRepository;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.security.SecurityUtil;
import com.javawwa25.app.springboot.web.dto.UserDto;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AccountService accountService;
	private final AuthorityRepository authorityRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteUserById(long id) {
		// TODO Auto-generated method stub

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
								.registrationDate(LocalDate.now())
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
		user.getAccount().setLastActiveDate(LocalDate.now());
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
	public void fillUserDtoModel(Model model) {
		UserDto dto = getLoggedUserDto();
		model.addAttribute("user", dto);
	}
	
	@Override
	public void fillAllUsersForAdmin(Model model) {
		UserDto dto = getLoggedUserDto();
		model.addAttribute("user", dto);
		model.addAttribute("userList", getAllUsers());
	}
	

	@Override
	public UserDto getLoggedUserDto() {
		return setUserDetailsDto(getLoggedUser());

	}
	
	private UserDto setUserDetailsDto(User user) {
		UserDto dto = new UserDto();
		dto.setAccountId(user.getAccount().getAccountId());
		dto.setFirstName(user.getLastName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getAccount().getEmail());
		return dto;
	}

	@Override
	public User save(UserDto dto) {
		Account account = accountService.save(
				Account.builder()
				.authority(setAuthority(dto.isAdmin()))
				.email(dto.getEmail())
				.password(passwordEncoder.encode(dto.getPassword()))
				.registrationDate(LocalDate.now())
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

	@Override
	public void fillUserDtoRegistrationModel(Model model) {
		UserDto dto = getLoggedUserDto();
		model.addAttribute("user", dto);
		model.addAttribute("dto", new UserRegistrationDto());
	}

	@Override
	public void fillUserDtoRegistrationModel(UserRegistrationDto dto, Model model) {
		UserDto userDto = getLoggedUserDto();
		model.addAttribute("user", userDto);
		model.addAttribute("dto", dto);
	}
}
