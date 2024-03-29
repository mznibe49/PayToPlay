package com.app.backend.repository;

import java.util.Optional;

import com.app.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    // User findByResetPasswordToken(String token);

    Optional<User> findByEmail(String email);

    Optional<User> findUserById(long id);
}