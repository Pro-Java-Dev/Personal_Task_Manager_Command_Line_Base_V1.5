package PERSONAL_TASK_MANAGER;

import PERSONAL_TASK_MANAGER.database.DatabaseConnection;
import PERSONAL_TASK_MANAGER.storage.TaskRepository;
import PERSONAL_TASK_MANAGER.services.TaskService;
import PERSONAL_TASK_MANAGER.ui.ConsolUI;

public class App {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();

        TaskRepository taskRepository = new TaskRepository(db.getConnection());
        TaskService taskService = new TaskService(taskRepository);
        ConsolUI ui = new ConsolUI(taskService);

        ui.start();

        db.close();
    }
}
