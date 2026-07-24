package com.yashdotdev.analytic_service.mapper;



import com.yashdotdev.analytic_service.entity.ClickEvent;
import com.yashdotdev.common.events.ClickEvents;
import org.springframework.stereotype.Component;

@Component
public class ClickEventMapper {

    public ClickEvent toEntity(ClickEvents event) {

        return ClickEvent.builder()
                .shortCode(event.shortCode())
                .originalUrl(event.originalUrl())
                .userId(event.userId())
                .ipAddress(event.ipAddress())
                .userAgent(event.userAgent())
                .referer(event.referer())
                .clickedAt(event.clickedAt())
                .build();
    }
}
