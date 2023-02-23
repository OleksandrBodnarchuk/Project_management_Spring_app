package com.javawwa25.app.springboot.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javawwa25.app.springboot.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT max(a.id) from Account a")
	Long getNextId();
	
	Optional<Account> findByEmail(String email);
}
