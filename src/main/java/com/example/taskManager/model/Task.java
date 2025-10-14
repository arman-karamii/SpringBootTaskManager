package com.example.taskManager.model;

public class Task {
    private Long id;
    private String title;
    private Boolean isDone;

    public Task() {}

    public Task(Long id, String title, Boolean isDone) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }
}
