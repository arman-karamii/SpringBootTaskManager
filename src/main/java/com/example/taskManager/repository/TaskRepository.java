package com.example.taskManager.repository;

import com.example.taskManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Basic queries
    List<Task> findByIsDone(Boolean isDone);
    
    List<Task> findByTitleContainingIgnoreCase(String title);
    
    List<Task> findByDescriptionContainingIgnoreCase(String description);
    
    // Oracle-specific queries with ROWNUM for pagination
    @Query(value = "SELECT * FROM (SELECT * FROM TASKS WHERE IS_DONE = :isDone ORDER BY CREATED_AT DESC) WHERE ROWNUM <= :limit", 
           nativeQuery = true)
    List<Task> findRecentTasksByStatus(@Param("isDone") Boolean isDone, @Param("limit") int limit);
    
    // Date range queries
    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :startDate AND :endDate")
    List<Task> findTasksByDueDateRange(@Param("startDate") LocalDateTime startDate, 
                                      @Param("endDate") LocalDateTime endDate);
    
    // Oracle-specific bulk update
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.isDone = true, t.updatedAt = CURRENT_TIMESTAMP WHERE t.id IN :ids")
    int markTasksAsCompleted(@Param("ids") List<Long> ids);
    
    // Complex query with Oracle functions
    @Query(value = "SELECT t.* FROM TASKS t WHERE UPPER(t.TITLE) LIKE UPPER('%' || :keyword || '%')", 
           nativeQuery = true)
    List<Task> findTasksByTitleKeyword(@Param("keyword") String keyword);
    
    // Count queries
    @Query("SELECT COUNT(t) FROM Task t WHERE t.isDone = false")
    Long countPendingTasks();
    
    @Query("SELECT COUNT(t) FROM Task t WHERE t.isDone = true")
    Long countCompletedTasks();
    
    // Priority-based queries
    @Query("SELECT t FROM Task t WHERE t.priority = :priority ORDER BY t.createdAt DESC")
    List<Task> findByPriorityOrderByCreatedAtDesc(@Param("priority") String priority);
    
    // Overdue tasks
    @Query("SELECT t FROM Task t WHERE t.dueDate < CURRENT_TIMESTAMP AND t.isDone = false")
    List<Task> findOverdueTasks();
    
    // Tasks due today (compare date portion of dueDate with today)
    @Query("SELECT t FROM Task t WHERE CAST(t.dueDate AS date) = CURRENT_DATE AND t.isDone = false")
    List<Task> findTasksDueToday();
    
    // Oracle-specific: Get tasks with estimated hours
    @Query("SELECT t FROM Task t WHERE t.estimatedHours IS NOT NULL ORDER BY t.estimatedHours DESC")
    List<Task> findTasksWithEstimatedHours();
    
    // Search across multiple fields
    @Query(value = "SELECT t.* FROM TASKS t WHERE " +
           "UPPER(t.TITLE) LIKE UPPER('%' || :searchTerm || '%') OR " +
           "UPPER(t.DESCRIPTION) LIKE UPPER('%' || :searchTerm || '%')", 
           nativeQuery = true)
    List<Task> searchTasks(@Param("searchTerm") String searchTerm);
}
