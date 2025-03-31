package org.spring.authenticationservice.repository.security;

import org.spring.authenticationservice.model.security.TokenType;
import org.spring.authenticationservice.model.security.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByTokenAndTokenType(String token, TokenType tokenType);
    Optional<VerificationToken> findByEmailAndTokenType(String email, TokenType tokenType);
    void deleteByEmailAndTokenType(String email, TokenType tokenType);
    VerificationToken findByToken(String token);
}
