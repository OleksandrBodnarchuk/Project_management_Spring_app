package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.services.UserService;
import com.javawwa25.app.springboot.web.dto.UserLoginDto;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
	
	@Mock
	private Model model;
	@Mock
	private UserService userService;
	@InjectMocks
	private LoginController underTest;
	
	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(underTest).build();

	}

	@Test
	void testLogin() throws Exception {
		String loginPage = "login-page";
		mvc.perform(get("/login"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name(loginPage));
		String login = underTest.login(model);
		verify(model,times(1)).addAttribute(eq("dto"), any());
		
		assertEquals(loginPage, login);
	}

	@Test
	void testLoginUser() throws Exception {
		String email = "email";
		String password = "password";
		UserLoginDto dto = new UserLoginDto();
		dto.setUsername(email);
		dto.setPassword(password);
		User user = User.builder().email(email).password(password).build();
		user.setId(1L);

		when(userService.findByEmail(anyString())).thenReturn(user);

		String redirectUrl = "redirect:/user/" + user.getId();

		mvc.perform(post("/login")
				.flashAttr("dto", dto))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(redirectUrl));
		
		verify(userService, times(1)).findByEmail(anyString());
	}

}
