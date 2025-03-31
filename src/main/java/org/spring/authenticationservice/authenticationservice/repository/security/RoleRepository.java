package org.spring.authenticationservice.repository.security;

import org.spring.authenticationservice.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String donor);
}
