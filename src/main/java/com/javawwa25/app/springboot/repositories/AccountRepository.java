package com.javawwa25.app.springboot.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.models.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

	@Query("SELECT max(a.id) from Account a")
	long getNextId();
}
