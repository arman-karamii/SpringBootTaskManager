package com.example.taskManager.controller;

import com.example.taskManager.model.Task;
import com.example.taskManager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
class TaskControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TaskService taskService;

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        taskService.clearAllTasks(); // Clear state before each test
    }

    @Test
    void shouldReturnEmptyTaskListInitially() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldCreateTask() throws Exception {
        String taskJson = "{\"title\":\"New Task\",\"isDone\":false}";

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.isDone").value(false));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        // First create a task
        String taskJson = "{\"title\":\"Test Task\",\"isDone\":false}";
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated());

        // Then get it by ID
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.isDone").value(false));
    }

    @Test
    void shouldReturnNotFoundForNonExistentTask() throws Exception {
        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // First create a task
        String taskJson = "{\"title\":\"Original Task\",\"isDone\":false}";
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated());

        // Then update it
        String updateJson = "{\"title\":\"Updated Task\",\"isDone\":true}";
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.isDone").value(true));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // First create a task
        String taskJson = "{\"title\":\"Task to Delete\",\"isDone\":false}";
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated());

        // Then delete it
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());

        // Verify it's gone
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());
    }
}
