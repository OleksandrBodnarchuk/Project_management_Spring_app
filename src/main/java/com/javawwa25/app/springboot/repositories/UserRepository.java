package com.javawwa25.app.springboot.repositories;

import com.javawwa25.app.springboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
