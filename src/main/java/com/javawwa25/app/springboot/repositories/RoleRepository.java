package com.javawwa25.app.springboot.repositories;

import com.javawwa25.app.springboot.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
