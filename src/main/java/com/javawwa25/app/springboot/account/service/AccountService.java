package com.javawwa25.app.springboot.account.service;

import java.util.Optional;

import com.javawwa25.app.springboot.account.Account;

public interface AccountService {

	Account save(Account account);
	Optional<Account> findByUsername(String username);
}
