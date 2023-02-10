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
import org.springframework.validation.BindingResult;

import com.javawwa25.app.springboot.security.validators.EmailValidator;
import com.javawwa25.app.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserRegistrationControllerTest {

	@Mock
	private Model model;
	@Mock
	private UserService userService;
	@Mock
	private BindingResult result;
	@Mock 
	private EmailValidator emailValidator;
	
	@InjectMocks
	private UserRegistrationController underTest;

	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(underTest).build();

	}

	@Test
	void testGetRegisterForm() throws Exception {
		String expectedTemplate = "register";
		mvc.perform(get("/registration"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name(expectedTemplate));
	}
}
