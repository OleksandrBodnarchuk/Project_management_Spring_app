package com.javawwa25.app.springboot.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javawwa25.app.springboot.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT max(a.id) from Account a")
	Long getNextId();

	Optional<Account> findByEmail(String email);

	@Query("SELECT count(a.id) from Account a where a.email = :email")
	int findAccountEmail(@Param(value = "email") String email);
}
