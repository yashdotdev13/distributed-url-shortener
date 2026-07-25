package com.yashdotdev.analytic_service.entity.ownership;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "url_ownership")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlOwnership {

    @Id
    @Column(name = "short_code", nullable = false, length = 12)
    private String shortCode;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
}