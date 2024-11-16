package com.codecraft.Crud_app.service;

import com.codecraft.Crud_app.model.Task;
import com.codecraft.Crud_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @PreAuthorize("hasRole('ADMIN') || hasRole('PERSON')")
    public List<Task> getAllEmployees() {
        return taskRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('PERSON')")
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}