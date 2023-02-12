package com.javawwa25.app.springboot.services;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.models.Account;
import com.javawwa25.app.springboot.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account save(Account account) {
		Long nextId = accountRepository.getNextId();
		if(nextId == null) {
			nextId = 1L;
		}
		account.setAccountId(++nextId);
		return accountRepository.save(account);
	}

}