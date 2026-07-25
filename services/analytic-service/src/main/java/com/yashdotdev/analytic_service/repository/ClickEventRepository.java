package com.yashdotdev.analytic_service.repository;

import com.yashdotdev.analytic_service.entity.raw.ClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ClickEventRepository extends JpaRepository<ClickEvent, Long> {
}
