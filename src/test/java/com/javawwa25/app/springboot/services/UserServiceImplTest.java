package com.javawwa25.app.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Account;
import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.UserRepository;
import com.javawwa25.app.springboot.web.dto.UserDto;
import com.javawwa25.app.springboot.web.dto.UserRegistrationDto;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	
	@Mock
	private UserService userService;
	@Mock
	private AccountService accountService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserServiceImpl underTest;
	
	private User user;
	private Account account;
	private List<User> users = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		user = new User();
		user.setId(1L);
		account = new Account();
		account.setAccountId(1L);
		account.setEmail("accountEmail@email.com");
		user.setAccount(account);
		
		User user2 = new User();
		user2.setId(2L);
		
		Project javaProject = Project.builder()
				.startDate(LocalDate.now())
				.name("Java")
				.info("Description")
				.build();
		javaProject.setId(1L);
		javaProject.setUsers(Set.of(user));
		
		Project pythonProject = Project.builder()
				.startDate(LocalDate.now())
				.name("Python")
				.info("Description")
				.build();
		pythonProject.setId(1L);
		pythonProject.setUsers(Set.of(user));
		user.addProject(javaProject);
		user2.addProject(pythonProject);
		
		users.addAll(List.of(user,user2));
	}

	@Test
	void testGetAllUsers() {
		given(userRepository.findAll()).willReturn(users);
		List<User> allUsers = underTest.getAllUsers();
		assertEquals(users.size(), allUsers.size());
	}

	@Test
	void testSave() {
		User user = users.get(0);
		underTest.save(user);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void testGetUserById() {
		given(userRepository.findById(anyLong())).willReturn(Optional.of(users.get(1)));
		User user = underTest.getUserById(1L);
		verify(userRepository, times(1)).findById(anyLong());
		assertNotNull(user);
	}
	
	@Test
	void testGetLoggedUserId() {
		SecurityContext context = mock(SecurityContext.class);
		SecurityContextHolder.setContext(context);
		given(context.getAuthentication()).willReturn(new UsernamePasswordAuthenticationToken("email","password"));
		given(underTest.findByEmail(anyString())).willReturn(user);
		underTest.getLoggedUserId();
		verify(userRepository,times(1)).findByAccountEmail(anyString());
	}
	
	@Test
	void testSaveRegister() {
		UserRegistrationDto dto = new UserRegistrationDto();
		dto.setFirstName(users.get(0).getFirstName());
		dto.setLastName("test");
		dto.setEmail(users.get(0).getAccount().getEmail());
		dto.setPassword("test");
		underTest.saveRegister(dto);
		verify(accountService, times(1)).save(any());
		assertEquals(dto.getEmail(), this.user.getAccount().getEmail());
		
	}
	
	@Test
	void testUserLogged() {
		User user = mock(User.class);
		Account account = mock(Account.class);
		user.setAccount(account);
		SecurityContext context = mock(SecurityContext.class);
		SecurityContextHolder.setContext(context);
		given(context.getAuthentication()).willReturn(new UsernamePasswordAuthenticationToken("email","password"));
		given(underTest.findByEmail(anyString())).willReturn(this.user);
		
		underTest.userLogged();
		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	void testUpdate() {
		SecurityContext context = mock(SecurityContext.class);
		SecurityContextHolder.setContext(context);
		given(context.getAuthentication()).willReturn(new UsernamePasswordAuthenticationToken("email","password"));
		given(underTest.findByEmail(anyString())).willReturn(user);
		UserDto dto = new UserDto();
		dto.setAccountId(user.getAccount().getAccountId());
		underTest.update(dto);
		verify(userRepository, times(1)).save(user);
	}
	
	@Test
	void testUpdate_withErrors() {
		SecurityContext context = mock(SecurityContext.class);
		SecurityContextHolder.setContext(context);
		given(context.getAuthentication()).willReturn(new UsernamePasswordAuthenticationToken("email", "password"));
		given(underTest.findByEmail(anyString())).willReturn(user);

		UserDto userDto = new UserDto();
		userDto.setAccountId(23);

		assertThrows(RuntimeException.class, () -> {
			underTest.update(userDto);
		});

		verify(userRepository, never()).save(user);
	}
	
	@Test
	void testFillUserDtoModel() {
		Model model = mock(Model.class);
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
		underTest.fillUserDtoModel(user.getAccount().getAccountId(), model);
		verify(model, times(1)).addAttribute(anyString(), any());
	}

	@Test
	void testFillUserDtoModel_willThrow() {
		Model model = mock(Model.class);
		given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
		assertThrows(RuntimeException.class, () -> {
			underTest.fillUserDtoModel(33, model);
		});
	}

}
