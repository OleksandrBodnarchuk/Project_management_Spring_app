package com.javawwa25.app.springboot.repositories;

import com.javawwa25.app.springboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // will retrieve user object by Email
    User findByEmail(String email);

}
