package com.yashdotdev.auth_service.repository;

import com.yashdotdev.auth_service.entity.RefreshToken;
import com.yashdotdev.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {


    Optional<RefreshToken> findByToken(String token);


    List<RefreshToken>  findByUser(User user);

    void deleteAllUser(User user);

    void deleteAllByExpiresAtBefore(Instant instant);
}
