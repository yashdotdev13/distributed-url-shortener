package com.yashdotdev.analytic_service.repository;

import com.yashdotdev.analytic_service.entity.ownership.UrlOwnership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlOwnershipRepository
        extends JpaRepository<UrlOwnership, String> {
}