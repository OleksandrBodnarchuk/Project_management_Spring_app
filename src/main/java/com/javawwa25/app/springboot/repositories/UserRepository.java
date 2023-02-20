package com.javawwa25.app.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javawwa25.app.springboot.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByAccountEmail(String email);

	User findByAccountAccountId(long accountId);
}
