package PERSONAL_TASK_MANAGER.ui;

import PERSONAL_TASK_MANAGER.services.TaskService;
import PERSONAL_TASK_MANAGER.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsolUI {
    private final TaskService taskservices;
    private final Scanner scanner;

    public ConsolUI(TaskService taskservices) {
        this.taskservices = taskservices;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while(running) {
            printMenue();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    listTasks();
                    break;
                case "3":
                    markTaskCompleted();
                    break;
                case "4":
                    deleteTask();
                    break;
                case "5":
                    filterByPriority();
                    break;
                case "6":
                    filterByCategory();
                    break;
                case "7":
                    sortByDueDate();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting the program. Goodbye!❤️");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }

    private void printMenue() {
        System.out.println("\n===============  The tasks management system ===============");
        System.out.println("1.Insert new task");
        System.out.println("2.Show all task");
        System.out.println("3.Mark task as completed");
        System.out.println("4.Delete task");
        System.out.println("5.Filter task based on priority");
        System.out.println("6.Filter task based on category");
        System.out.println("7.Show tasks sorted by due date");
        System.out.println("0.Exit");
        System.out.println("=============================================================");
    }

    private void addTask() {
        System.out.println("\n--- Insert-New-Task---");

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Set priority: (LOW, MEDIUM, HIGH): ");
        Priority priority = parsPriority(scanner.nextLine().trim());

        System.out.print("Category type: (\"Work\" OR \"Personal\")");
        String categoryName = scanner.nextLine().trim();
        Category category = categoryName.isEmpty() ? null : new Category(categoryName);

        System.out.print("Enter the due date (YYYY-MM-DD Leave blank to opt out): ");
        LocalDate dueDate = parseDate(scanner.nextLine().trim());

        Task task = taskservices.addTask(title, description, priority, category, dueDate);
        System.out.println("The task was successfully registered" + task.getId());

    }

    private void listTasks() {
        System.out.println("\n--- List all tasks ---");
        List<Task> tasks = taskservices.getAllTask();
        displayTask(tasks);
    }

    private void markTaskCompleted() {
        System.out.print("\nEnter task ID: ");
        Long id = parseLong(scanner.nextLine().trim());
        if (id != null && taskservices.markTaskAsCompleted(id)) {
            System.out.println("Task's status changed to completed.");
        } else {
            System.out.println("There is no task with this id.");
        }
    }

    private void deleteTask() {
        System.out.print("\nEnter the task id that you want to delete: ");
        Long id = parseLong(scanner.nextLine().trim());
        if (id != null && taskservices.deleteTask(id)) {
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("There is no task with this id.");
        }
    }

    private void filterByPriority() {
        System.out.println("Enter the priority (LOW, MEDIUM, HIGH): ");
        Priority priority = parsPriority(scanner.nextLine().trim());
        List<Task> priorityTask = taskservices.filterByPriority(priority);
        System.out.println("\n--- Tasks with " + priority + " priority ---");
        displayTask(priorityTask);
    }

    private void filterByCategory() {
        System.out.print("\nCategory name: ");
        String categoryName = scanner.nextLine().trim();
        
        List<Task> categoryTask = taskservices.filterByCategory(new Category(categoryName));
        System.out.println("\nTask in " + categoryName + " category");
        displayTask(categoryTask);
    }

    private void sortByDueDate() {
        System.out.println("\n--- All tasks sorted by due date.");
        List<Task> dueDateTasks = taskservices.getTaskSortedByDuedate();
        displayTask(dueDateTasks);
    }

    private void displayTask(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Nothing tasks here to show you.");
            return;
        }
        for (Task task : tasks) {
            String status = task.isCompleted() ? "[Done ✔]" : "[Not done ✘]";
            String categoryStr = task.getCategory() != null ? task.getCategory().getName() : "Without category";
            String dueDateStr = task.getDueDate() != null ? task.getDueDate().toString() : "ٌWithout date";

            System.out.printf("ID: %d | %s %s | priority: %s | category: %s | date: %s",
                              task.getId(), status, task.getTitle(), task.getPriority().getDisplayName(), categoryStr, dueDateStr);
            if (task.getDescription() != null && !task.getDescription().isEmpty()) {
                System.out.println("Description: " + task.getDescription());
            }
        }
    }

    private Priority parsPriority(String input) {
        try {
            return Priority.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Set to default (MIDIEM)");
            return Priority.MEDIUM;
        }
    }

    private LocalDate parseDate(String input) {
        if (input.isEmpty()) return null;
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Wrong format for date time, set to empty!");
            return null;
        }
    }

    private Long parseLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
}
