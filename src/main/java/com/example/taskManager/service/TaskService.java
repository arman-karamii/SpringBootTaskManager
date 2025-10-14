package com.example.taskManager.service;

import com.example.taskManager.model.Task;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public void clearAllTasks() {
        tasks.clear();
        counter.set(0);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Task createTask(Task task) {
        task.setId(counter.incrementAndGet());
        tasks.add(task);
        return task;
    }

    public Task getTaskById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        if (existingTask != null) {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setIsDone(updatedTask.getIsDone());
            return existingTask;
        }
        return null;
    }

    public boolean deleteTask(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }
}
