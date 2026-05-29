package com.example.taskManager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "TASKS")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "TASK_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(name = "TITLE", nullable = false, length = 255)
    private String title;
    
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    
    @Column(name = "IS_DONE", nullable = false)
    private Boolean isDone = false;
    
    @Column(name = "PRIORITY")
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;
    
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    
    @Column(name = "DUE_DATE")
    private LocalDateTime dueDate;
    
    // Oracle-specific: Use BigDecimal for precise decimal handling
    @Column(name = "ESTIMATED_HOURS", precision = 5, scale = 2)
    private BigDecimal estimatedHours;
    
    // Constructors
    public Task() {}
    
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isDone = false;
        this.createdAt = LocalDateTime.now();
        this.priority = Priority.MEDIUM;
    }
    
    public Task(Long id, String title, Boolean isDone) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
        this.createdAt = LocalDateTime.now();
        this.priority = Priority.MEDIUM;
    }
    
    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Boolean getIsDone() { return isDone; }
    public void setIsDone(Boolean isDone) { this.isDone = isDone; }
    
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    
    public BigDecimal getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(BigDecimal estimatedHours) { this.estimatedHours = estimatedHours; }
    
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isDone=" + isDone +
                ", priority=" + priority +
                ", createdAt=" + createdAt +
                ", dueDate=" + dueDate +
                '}';
    }
}

