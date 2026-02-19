package com.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "Username obrigatório")
        @Size(min = 3, max = 150) String username,
        @NotBlank(message = "Senha obrigatória")
        @Size(min = 6) String password
) {}