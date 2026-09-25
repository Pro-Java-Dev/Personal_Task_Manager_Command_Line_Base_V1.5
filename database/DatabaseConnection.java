package PERSONAL_TASK_MANAGER.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:C:/VSCODE_PROJECTS/PERSONAL_TASK_MANAGER/taskmanager.db";
    private Connection connection;

    public DatabaseConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            createTable();
            System.out.println("Database connected successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
}

    private void createTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id          INTEGER PRIMARY KEY AUTOINCREMENT,
                    title       TEXT    NOT NULL,
                    description TEXT,
                    priority    TEXT    NOT NULL,
                    category    TEXT,
                    due_date    TEXT,
                    completed   INTEGER NOT NULL DEFAULT 0
                )
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
