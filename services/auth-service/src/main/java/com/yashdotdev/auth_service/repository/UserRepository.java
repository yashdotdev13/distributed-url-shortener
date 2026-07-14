package com.yashdotdev.auth_service.repository;

import com.yashdotdev.auth_service.entity.User;
import com.yashdotdev.auth_service.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmailAndAccountStatus(String email,
                                               AccountStatus accountStatus);
}
