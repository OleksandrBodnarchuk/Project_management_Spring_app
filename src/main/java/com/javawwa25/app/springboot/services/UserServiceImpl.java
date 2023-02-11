package com.javawwa25.app.springboot.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Account;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.security.SecurityUtil;
import com.javawwa25.app.springboot.web.dto.UserDto;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AccountService accountService;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, 
			AccountService accountService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.accountService = accountService;
	}

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

	@Override
	public User saveRegister(UserRegistrationDto dto) {
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		Account account = new Account();
		account.setEmail(dto.getEmail());
		account.setPassword(passwordEncoder.encode(dto.getPassword()));
		account.setRegistrationDate(LocalDate.now());
		accountService.save(account);

		user.setAccount(account);
		return userRepository.save(user);
	}

	@Override
	public Long getLoggedUserId() {
		return findByEmail(SecurityUtil.getSessionUser()).getId();
	}

	@Override
	public void userLogged() {
		User user = findByEmail(SecurityUtil.getSessionUser());
		user.getAccount().setLastActiveDate(new Date());
		this.save(user);
	}

	@Override
	public void fillUserDtoModel(long userId, Model model) {
		User user = getUserById(userId);
		if (user.getAccount().getAccountId() != userId) {
			throw new RuntimeException("Wrong user id");
		} else {
			UserDto dto = new UserDto();
			dto.setAccountId(user.getAccount().getAccountId());
			dto.setFirstName(user.getLastName());
			dto.setLastName(user.getLastName());
			dto.setEmail(user.getAccount().getEmail());
			model.addAttribute("dto", dto);
		}
	}

	@Override
	public void update(UserDto dto) {
		User user = findByEmail(SecurityUtil.getSessionUser());
		if(user.getAccount().getAccountId() != dto.getAccountId()) {
			throw new RuntimeException("Id not match user id");
		} else {
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.getAccount().setEmail(dto.getEmail());
			this.save(user);
		}
	}
}
