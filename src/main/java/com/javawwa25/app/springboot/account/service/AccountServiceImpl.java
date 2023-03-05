package com.javawwa25.app.springboot.account.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.account.Account;
import com.javawwa25.app.springboot.user.repo.AccountRepository;

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
			account.setAccountId(nextId);
		} else {
			account.setAccountId(++nextId);	
		}
		return accountRepository.save(account);
	}

	@Override
	public Optional<Account> findByUsername(String username) {
		return accountRepository.findByEmail(username);
	}

	@Override
	public int findEmail(String email) {
		return accountRepository.findAccountEmail(email);
	}

}
