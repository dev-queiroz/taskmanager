package com.taskmanager.service;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.entity.Task;
import com.taskmanager.repository.TaskRepository;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> findAll() {
        return taskRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse create(@NonNull TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public TaskResponse findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        return toResponse(task);
    }

    public TaskResponse update(Long id, @NonNull TaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(request.title());
        task.setDescription(request.description());
        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskResponse> findByCompleted(boolean completed) {
        return taskRepository.findByCompleted(completed).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> findByCreatedAt(LocalDateTime createdAt) {
        return taskRepository.findByCreatedAt(createdAt).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Contract("_ -> new")
    private @NonNull TaskResponse toResponse(@NonNull Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt()
        );
    }
}
