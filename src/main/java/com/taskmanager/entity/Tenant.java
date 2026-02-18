package com.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tenants")
@Data
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Ex: "ACME Corp"

    @Column(unique = true)
    private String slug; // Ex: "acme-corp" (para subdomínios ou URLs)

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}