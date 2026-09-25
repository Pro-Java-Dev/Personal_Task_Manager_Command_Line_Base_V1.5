package PERSONAL_TASK_MANAGER.model;

import java.time.LocalDate;

public class Task {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Category category;
    private LocalDate dueDate;
    private boolean completed;

    public Task(String title, String description, Priority priority, Category category, LocalDate dueDate) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.category = category;
        this.dueDate = dueDate;
        this.completed = false;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
