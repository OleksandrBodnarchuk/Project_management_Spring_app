package com.javawwa25.app.springboot.user.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.dto.SimpleUserDto;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByAccountEmail(String email);

	User findByAccountAccountId(long accountId);

	@Query("SELECT new com.javawwa25.app.springboot.user.dto.SimpleUserDto(u.account.id, CONCAT (u.firstName,' ', u.lastName) as userName) FROM User u")
	List<SimpleUserDto> getUserDtoName();

	@Query("SELECT new com.javawwa25.app.springboot.user.dto.SimpleUserDto(u.account.id, CONCAT (u.firstName,' ', u.lastName) as userName) "
			+ "FROM User u WHERE u.account.id NOT IN (:accountIds)")
	List<SimpleUserDto> findByAccountAccountIdIn(@Param("accountIds") Set<Long> accountIds);
}
