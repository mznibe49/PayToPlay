package com.app.backend.repository;

import com.app.backend.models.PasswordResetToken;
import com.app.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    //Optional<PasswordResetToken> findByUser(User user);
    //Optional<PasswordResetToken> findByEmail(String email);
    Optional<PasswordResetToken> findByToken(String token);
}
