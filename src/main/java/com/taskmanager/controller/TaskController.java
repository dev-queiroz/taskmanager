package com.taskmanager.controller;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.findAll();
    }

    @PostMapping
    public TaskResponse create(TaskRequest request) {
        return taskService.create(request);
    }

    @GetMapping("/{id}")
    public TaskResponse getById(Long id) {
        return taskService.findById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(Long id, TaskRequest request) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        taskService.delete(id);
    }

    @GetMapping("/completed")
    public List<TaskResponse> getByCompleted(boolean completed) {
        return taskService.findByCompleted(completed);
    }

    @GetMapping("/date")
    public List<TaskResponse> getByCreatedAt(LocalDateTime createdAt) {
        return taskService.findByCreatedAt(createdAt);
    }
}
