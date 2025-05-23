package org.spring.authenticationservice.repository.security;

import jakarta.transaction.Transactional;
import org.spring.authenticationservice.model.security.AccessControl;
import org.spring.authenticationservice.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isEnabled = true WHERE u.email = :email")
    void enableUser(@Param("email") String email);

    List<User> findByAdminApproval(AccessControl adminApproval);
}
