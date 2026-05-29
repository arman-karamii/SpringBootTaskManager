package com.example.taskManager.service;

import com.example.taskManager.model.Task;
import com.example.taskManager.model.Priority;
import com.example.taskManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Service
@Transactional
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    public Task createTask(Task task) {
        // Set default values if not provided
        if (task.getIsDone() == null) {
            task.setIsDone(false);
        }
        if (task.getCreatedAt() == null) {
            task.setCreatedAt(LocalDateTime.now());
        }
        return taskRepository.save(task);
    }
    
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        // Update fields
        if (taskDetails.getTitle() != null) {
            task.setTitle(taskDetails.getTitle());
        }
        if (taskDetails.getDescription() != null) {
            task.setDescription(taskDetails.getDescription());
        }
        if (taskDetails.getIsDone() != null) {
            task.setIsDone(taskDetails.getIsDone());
        }
        if (taskDetails.getPriority() != null) {
            task.setPriority(taskDetails.getPriority());
        }
        if (taskDetails.getDueDate() != null) {
            task.setDueDate(taskDetails.getDueDate());
        }
        if (taskDetails.getEstimatedHours() != null) {
            task.setEstimatedHours(taskDetails.getEstimatedHours());
        }
        
        // UpdatedAt will be set automatically by @PreUpdate
        return taskRepository.save(task);
    }
    
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
    
    // Additional business logic methods
    
    public List<Task> getCompletedTasks() {
        return taskRepository.findByIsDone(true);
    }
    
    public List<Task> getPendingTasks() {
        return taskRepository.findByIsDone(false);
    }
    
    public List<Task> getTasksByPriority(String priority) {
        return taskRepository.findByPriorityOrderByCreatedAtDesc(priority);
    }
    
    public List<Task> getOverdueTasks() {
        return taskRepository.findOverdueTasks();
    }
    
    public List<Task> getTasksDueToday() {
        return taskRepository.findTasksDueToday();
    }
    
    public List<Task> searchTasks(String searchTerm) {
        return taskRepository.searchTasks(searchTerm);
    }
    
    public List<Task> getRecentTasksByStatus(Boolean isDone, int limit) {
        return taskRepository.findRecentTasksByStatus(isDone, limit);
    }
    
    public List<Task> getTasksByDueDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findTasksByDueDateRange(startDate, endDate);
    }
    
    public List<Task> getTasksWithEstimatedHours() {
        return taskRepository.findTasksWithEstimatedHours();
    }
    
    public int markTasksAsCompleted(List<Long> taskIds) {
        return taskRepository.markTasksAsCompleted(taskIds);
    }
    
    // Statistics methods
    public Long getPendingTaskCount() {
        return taskRepository.countPendingTasks();
    }
    
    public Long getCompletedTaskCount() {
        return taskRepository.countCompletedTasks();
    }
    
    public Long getTotalTaskCount() {
        return taskRepository.count();
    }
    
    // Business logic methods
    public Task markTaskAsCompleted(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setIsDone(true);
        return taskRepository.save(task);
    }
    
    public Task markTaskAsPending(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setIsDone(false);
        return taskRepository.save(task);
    }
    
    public Task updateTaskPriority(Long id, String priority) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        // Convert string to Priority enum
        try {
            Priority priorityEnum = Priority.valueOf(priority.toUpperCase());
            task.setPriority(priorityEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid priority: " + priority);
        }
        
        return taskRepository.save(task);
    }
    
    public Task updateTaskEstimatedHours(Long id, BigDecimal estimatedHours) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setEstimatedHours(estimatedHours);
        return taskRepository.save(task);
    }
}
