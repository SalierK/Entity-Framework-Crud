package com.codecraft.Crud_app;

import com.codecraft.Crud_app.controller.TaskController;
import com.codecraft.Crud_app.model.Task;
import com.codecraft.Crud_app.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig
@SpringBootTest
class CrudAppApplicationTests {

    @Autowired
    private TaskService taskService;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/crud-db");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "postgres");
    }

    @Test
    public void worktest() {
        int i = 1;
        assertNotNull(i);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test_getAllTasks() {
        TaskController taskController = new TaskController(taskService);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("title");
        task.setDescription("description");
        task.setStatus(1);
        taskService.saveTask(task);
        taskController.createTask(task);
        List<Task> list1 = taskController.getAllTask();
        System.out.println(list1);
        assertNotNull(list1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test_getTaskById() {
        TaskController taskController = new TaskController(taskService);
        ResponseEntity<Task> idTask = taskController.getTaskById(1L);
        assertNotNull(idTask);
    }
}