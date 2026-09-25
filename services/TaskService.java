package PERSONAL_TASK_MANAGER.services;

import PERSONAL_TASK_MANAGER.model.Task;
import PERSONAL_TASK_MANAGER.storage.TaskRepository;
import PERSONAL_TASK_MANAGER.model.Priority;
import PERSONAL_TASK_MANAGER.model.Category;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task addTask(String title, String description, Priority priority, Category category, LocalDate dueDate) {
        Task task = new Task(title, description, priority, category, dueDate);
        return repository.save(task);
    }

    public List<Task> getAllTask() {
        return repository.findAll();
    }

    public boolean markTaskAsCompleted(Long id) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setCompleted(true);
            repository.save(task);
            return true;
        }
        return false;
    }

    public boolean deleteTask(Long id) {
        return repository.removeById(id);
    }

    public List<Task> filterByPriority(Priority priority) {
        return repository.findAll().stream()
               .filter(task -> task.getPriority() == priority)
               .collect(Collectors.toList());
    }

    public List<Task> filterByCategory(Category category) {
        return repository.findAll().stream()
               .filter(task -> task.getCategory().equals(category))
               .collect(Collectors.toList());
    }

    public List<Task> getTaskSortedByDuedate() {
        return repository.findAll().stream()
               .filter(task -> task.getDueDate() != null)
               .sorted((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()))
            // .sorted(Comprator.comparing(Task::getDueDate))
               .collect(Collectors.toList());
    }
}
