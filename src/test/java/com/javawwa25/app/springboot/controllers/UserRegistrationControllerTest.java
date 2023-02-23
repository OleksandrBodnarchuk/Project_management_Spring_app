package com.javawwa25.app.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.javawwa25.app.springboot.security.validators.EmailValidator;
import com.javawwa25.app.springboot.user.controller.UserRegistrationController;
import com.javawwa25.app.springboot.user.dto.UserRegistrationDto;
import com.javawwa25.app.springboot.user.service.UserService;

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
	
	@Test
	void testRegister_hasErrors() {
		BindingResult result = mock(BindException.class);
		result.addError(new ObjectError("TEMP","TEMP"));
		given(result.hasErrors()).willReturn(true);
		UserRegistrationDto userDto = new UserRegistrationDto();
		String expected = "register";
		String actual = underTest.register(userDto, result, model);
		verify(model, times(1)).addAttribute(anyString(), any());
		verify(userService, never()).saveRegister(any());
		
		assertEquals(expected, actual);
	}
}
