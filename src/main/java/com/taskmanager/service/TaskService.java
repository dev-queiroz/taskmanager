package com.taskmanager.service;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.repository.TaskRepository;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> findAll() {
        User currentUser = getCurrentUser();
        return taskRepository.findByTenant(currentUser.getTenant()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse create(@NonNull TaskRequest request) {
        User currentUser = getCurrentUser();
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setCreatedBy(currentUser);
        task.setTenant(currentUser.getTenant());
        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public Task findById(Long id) {
        User currentUser = getCurrentUser();
        return taskRepository.findById(id)
                .filter(task -> task.getTenant().equals(currentUser.getTenant()))
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public TaskResponse update(Long id, @NonNull TaskRequest request) {
        Task task = findById(id);
        task.setTitle(request.title());
        task.setDescription(request.description());
        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    @Contract("_ -> new")
    public @NonNull TaskResponse toResponse(@NonNull Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        return (User) auth.getPrincipal();
    }
}