package com.example.taskManager.service;

import com.example.taskManager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void shouldReturnEmptyListInitially() {
        List<Task> tasks = taskService.getAllTasks();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldCreateTask() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setIsDone(false);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask.getId());
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals(false, createdTask.getIsDone());
        assertEquals(1, taskService.getAllTasks().size());
    }

    @Test
    void shouldGetTaskById() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setIsDone(false);

        Task createdTask = taskService.createTask(task);
        Task foundTask = taskService.getTaskById(createdTask.getId());

        assertNotNull(foundTask);
        assertEquals(createdTask.getId(), foundTask.getId());
        assertEquals("Test Task", foundTask.getTitle());
        assertEquals(false, foundTask.getIsDone());
    }

    @Test
    void shouldReturnNullForNonExistentTask() {
        Task task = taskService.getTaskById(999L);
        assertNull(task);
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task();
        task.setTitle("Original Task");
        task.setIsDone(false);

        Task createdTask = taskService.createTask(task);

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setIsDone(true);

        Task result = taskService.updateTask(createdTask.getId(), updatedTask);

        assertNotNull(result);
        assertEquals(createdTask.getId(), result.getId());
        assertEquals("Updated Task", result.getTitle());
        assertEquals(true, result.getIsDone());
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setIsDone(false);

        Task createdTask = taskService.createTask(task);
        boolean deleted = taskService.deleteTask(createdTask.getId());

        assertTrue(deleted);
        assertNull(taskService.getTaskById(createdTask.getId()));
        assertTrue(taskService.getAllTasks().isEmpty());
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentTask() {
        boolean deleted = taskService.deleteTask(999L);
        assertFalse(deleted);
    }
}
