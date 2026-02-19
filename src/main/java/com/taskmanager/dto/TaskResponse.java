package com.taskmanager.dto;

import com.taskmanager.entity.enums.Priority;
import com.taskmanager.entity.enums.Status;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Status status,
        Priority priority,
        LocalDateTime dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}