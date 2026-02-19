package com.taskmanager.repository;

import com.taskmanager.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    // Pode adicionar métodos custom depois, ex: findBySubdomain(String subdomain)
}