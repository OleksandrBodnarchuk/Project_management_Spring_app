package com.javawwa25.app.springboot.services;

import java.util.Optional;

import com.javawwa25.app.springboot.models.Account;

public interface AccountService {

	Account save(Account account);
	Optional<Account> findByUsername(String username);

}
