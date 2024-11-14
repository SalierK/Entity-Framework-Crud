package com.codecraft.Crud_app.controller;

import com.codecraft.Crud_app.model.Task;
import com.codecraft.Crud_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {


    private final TaskService taskService;

    @RequestMapping
    public List<Task> getAllTask() {
        return taskService.getAllEmployees();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
public Task createTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
       return taskService.getTaskById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setStatus(taskDetails.getStatus());

                    return new ResponseEntity<>(taskService.saveTask(task), HttpStatus.OK);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
