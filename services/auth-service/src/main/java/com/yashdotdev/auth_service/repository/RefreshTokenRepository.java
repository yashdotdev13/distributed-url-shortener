package com.yashdotdev.auth_service.repository;

import com.yashdotdev.auth_service.entity.RefreshToken;
import com.yashdotdev.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByTokenAndRevokedFalse(String token);

    List<RefreshToken> findAllByUser(User user);

    void deleteAllByUser(User user);

    void deleteAllByExpiresAtBefore(Instant instant);

}