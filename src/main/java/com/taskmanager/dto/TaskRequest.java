package com.taskmanager.dto;

import com.taskmanager.entity.enums.Priority;
import com.taskmanager.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank String title,
        String description,
        Status status,
        Priority priority,
        LocalDateTime dueDate
) {}