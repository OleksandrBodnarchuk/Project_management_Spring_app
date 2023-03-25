package com.javawwa25.app.springboot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.javawwa25.app.springboot.controller.LoginController;
import com.javawwa25.app.springboot.user.service.UserService;


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
	}
}
