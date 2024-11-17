package com.codecraft.Crud_app;

import com.codecraft.Crud_app.controller.TaskController;
import com.codecraft.Crud_app.model.Task;
import com.codecraft.Crud_app.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig
@SpringBootTest
@EnableMethodSecurity

class CrudAppApplicationTests {

    @Autowired
    private TaskService taskService;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/crud-db");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "postgres");
    }

    @BeforeEach
    public void setUp() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setStatus(0);
        taskService.saveTask(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setStatus(1);
        taskService.saveTask(task2);
    }

    @AfterEach
    public void tearDown() {
        for (Task task : taskService.getAllTasks()) {
            taskService.deleteTaskById(task.getId());
        }
    }


    @Test
    @Order(1)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test_getTaskById() {
        ResponseEntity<Task> idTask = null;
        TaskController taskController = new TaskController(taskService);
        for (Task task : taskService.getAllTasks()) {
            idTask = taskController.getTaskById(task.getId());
            if (idTask.getBody().getId() != null) {
                break;
            }
        }
        assertNotNull(idTask.getBody().getId());
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test_getAllTasks() {
        TaskController taskController = new TaskController(taskService);
        List<Task> list = taskController.getAllTask();
        assertNotNull(list.get(0).getId());
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test_createTask() {
        Task task = new Task();
        String title = "Test title for creatTask";
        String description = "Test description for creatTask";
        int status = 654321;
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        TaskController taskController = new TaskController(taskService);
        Task newTask = taskController.createTask(task);
        assertEquals(title, taskController.getTaskById(newTask.getId()).getBody().getTitle());
        assertEquals(description, taskController.getTaskById(newTask.getId()).getBody().getDescription());
        assertEquals(status, taskController.getTaskById(newTask.getId()).getBody().getStatus());
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test_updateTask() {
        Task task = new Task();
        String title = "Test title for updateTask";
        String description = "Test description for updateTask";
        int status = 654321;
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        TaskController taskController = new TaskController(taskService);
        Task newTask = taskController.createTask(task);
        String newTitle = "New title for updateTask";
        String newDescription = "New description for updateTask";
        int newStatus = 123456;
        Task taskDetails = new Task();
        taskDetails.setTitle(newTitle);
        taskDetails.setDescription(newDescription);
        taskDetails.setStatus(newStatus);
        Task updatedTask = taskController.updateTask(newTask.getId(), taskDetails).getBody();
        assertEquals(newTitle, updatedTask.getTitle());
        assertEquals(newDescription, updatedTask.getDescription());
        assertEquals(newStatus, updatedTask.getStatus());
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test_deleteTaskById() {
        Task task = new Task();
        String title = "Test title for deleteTaskById";
        String description = "Test description for deleteTaskById";
        int status = 654321;
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        TaskController taskController = new TaskController(taskService);
        Task newTask = taskController.createTask(task);
        taskController.deleteTaskById(newTask.getId());
        assertEquals(null, taskController.getTaskById(newTask.getId()).getBody());
    }

    @Test
    @Order(6)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void test_deleteTaskById_notFound() {
        TaskController taskController = new TaskController(taskService);
        ResponseEntity<HttpStatus> response = taskController.deleteTaskById(999999999L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(7)
    @WithMockUser(username = "person", roles = {"PERSON"})
    public void test_getAllTasks_person() {
        TaskController taskController = new TaskController(taskService);
        List<Task> list = taskController.getAllTask();
        assertNotNull(list.get(0).getId());
    }

    @Test
    @Order(8)
    @WithMockUser(username = "person", roles = {"PERSON"})
    public void test_getTaskById_person() {
        ResponseEntity<Task> idTask = null;
        TaskController taskController = new TaskController(taskService);
        for (Task task : taskService.getAllTasks()) {
            idTask = taskController.getTaskById(task.getId());
            if (idTask.getBody().getId() != null) {
                break;
            }
        }
        assertNotNull(idTask.getBody().getId());
    }

    @Test
    @Order(8)
    @WithMockUser(username = "person", roles = {"PERSON"})
    public void test_createTask_person() {
        Task task = new Task();
        String title = "Test title for creatTask";
        String description = "Test description for creatTask";
        int status = 654321;
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        TaskController taskController = new TaskController(taskService);
        Task newTask = taskController.createTask(task);
        assertEquals(title, taskController.getTaskById(newTask.getId()).getBody().getTitle());
        assertEquals(description, taskController.getTaskById(newTask.getId()).getBody().getDescription());
        assertEquals(status, taskController.getTaskById(newTask.getId()).getBody().getStatus());
    }

    @Test
    @Order(9)
    @WithMockUser(username = "person", password = "personpassword", roles = {"PERSON"})
    public void testt() {
        int i = 1;
        assertNotNull(i);
    }
}