package com.javawwa25.app.springboot.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.web.dto.UserDtoName;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByAccountEmail(String email);

	User findByAccountAccountId(long accountId);

	@Query("SELECT u.id, CONCAT(u.firstName,' ',u.lastName) as userName FROM User u")
	Set<UserDtoName> getUserDtoName();
}
