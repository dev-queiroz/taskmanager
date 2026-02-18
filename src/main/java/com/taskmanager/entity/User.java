package com.taskmanager.entity;

import com.taskmanager.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {  // Implementa UserDetails pra Spring Security usar diretamente

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;  // Ou email

    @Column(nullable = false)
    private String password;  // Hashed com BCrypt

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)  // Roles carregadas sempre
    private List<Role> roles;  // Enum de roles (USER, ADMIN)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @OneToMany(mappedBy = "user")
    private List<Task> createdTasks;

    // Implementação de UserDetails (obrigatória)
    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}