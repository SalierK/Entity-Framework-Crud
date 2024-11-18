package com.codecraft.Crud_app.service;

import com.codecraft.Crud_app.model.Task;
import com.codecraft.Crud_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Secured({"ROLE_ADMIN", "ROLE_PERSON"})
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Secured({"ROLE_ADMIN", "ROLE_PERSON"})
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Secured({"ROLE_ADMIN"})
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Secured({"ROLE_ADMIN"})
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}