package PERSONAL_TASK_MANAGER.storage;

import PERSONAL_TASK_MANAGER.model.Category;
import PERSONAL_TASK_MANAGER.model.Priority;
import PERSONAL_TASK_MANAGER.model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

public class TaskRepository {
    private final Connection connection;

    public TaskRepository(Connection connection) {
        this.connection = connection;
    }

    // ── Save (insert new) or update (existing id) ──────────────────────
    public Task save(Task task) {
        if (task.getId() == null) {
            return insert(task);
        } else {
            update(task);
            return task;
        }
    }

    private Task insert(Task task) {
        String sql = "INSERT INTO tasks (title, description, priority, category, due_date, completed) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(ps, task);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    task.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting task: " + e.getMessage());
        }
        return task;
    }

    private void update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, priority = ?, "
                   + "category = ?, due_date = ?, completed = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParameters(ps, task);
            ps.setLong(7, task.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating task: " + e.getMessage());
        }
    }

    // ── Find ───────────────────────────────────────────────────────────
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tasks: " + e.getMessage());
        }
        return tasks;
    }

    public Optional<Task> findById(long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding task: " + e.getMessage());
        }
        return Optional.empty();
    }

    // ── Delete ─────────────────────────────────────────────────────────
    public boolean removeById(long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting task: " + e.getMessage());
            return false;
        }
    }

    public void clear() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM tasks");
        } catch (SQLException e) {
            System.err.println("Error clearing tasks: " + e.getMessage());
        }
    }

    // ── Helpers ────────────────────────────────────────────────────────
    private void setParameters(PreparedStatement ps, Task task) throws SQLException {
        ps.setString(1, task.getTitle());
        ps.setString(2, task.getDescription());
        ps.setString(3, task.getPriority().name());
        ps.setString(4, task.getCategory() != null ? task.getCategory().getName() : null);
        ps.setString(5, task.getDueDate() != null ? task.getDueDate().toString() : null);
        ps.setInt(6, task.isCompleted() ? 1 : 0);
    }

    private Task mapRow(ResultSet rs) throws SQLException {
        String priorityStr = rs.getString("priority");
        String categoryStr = rs.getString("category");
        String dueDateStr  = rs.getString("due_date");

        Priority priority = Priority.valueOf(priorityStr);
        Category category = categoryStr != null ? new Category(categoryStr) : null;
        LocalDate dueDate = dueDateStr != null ? LocalDate.parse(dueDateStr) : null;

        Task task = new Task(
            rs.getString("title"),
            rs.getString("description"),
            priority,
            category,
            dueDate
        );
        task.setId(rs.getLong("id"));
        task.setCompleted(rs.getInt("completed") == 1);
        return task;
    }
}
