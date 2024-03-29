package com.javawwa25.app.springboot.account.service;

import java.util.Optional;

import com.javawwa25.app.springboot.account.Account;
import com.javawwa25.app.springboot.file.FileData;

public interface AccountService {

	Account save(Account account);

	Optional<Account> findByUsername(String username);

	int findEmail(String email);

	void updateAvatar(Account account, FileData fileData);
}
