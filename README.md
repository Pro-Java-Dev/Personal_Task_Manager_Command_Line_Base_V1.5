# Personal Task Manager

A simple command-line task manager written in Java. It lets you create, list,
complete, delete, filter, and sort personal tasks from an interactive console
menu. Tasks are persisted to a local SQLite database, so they're still there
the next time you start the program.

## Features

- Add a task with a title, description, priority, category, and optional due date
- List all tasks
- Mark a task as completed
- Delete a task by ID
- Filter tasks by priority (`LOW`, `MEDIUM`, `HIGH`)
- Filter tasks by category (e.g. `Work`, `Personal`)
- Show all tasks sorted by due date

## Project structure

```
PERSONAL_TASK_MANAGER/
├── App.java                    # Entry point, wires up the app
├── model/
│   ├── Task.java                # Task entity
│   ├── Priority.java            # LOW / MEDIUM / HIGH enum
│   └── Category.java            # Task category
├── database/
│   └── DatabaseConnection.java  # SQLite connection setup and schema creation
├── services/
│   └── TaskService.java         # Business logic (add, filter, sort, complete, delete)
├── storage/
│   └── TaskRepository.java      # SQLite-backed storage (JDBC)
├── ui/
│   └── ConsolUI.java             # Console menu and user interaction
├── sqlite-jdbc-3.53.4.0.jar     # SQLite JDBC driver
└── taskmanager.db               # SQLite database file (created on first run)
```

The app follows a simple layered design: `ui` handles console I/O, `services`
holds the task-management logic, `storage` persists tasks to SQLite via JDBC,
`database` manages the connection and schema, and `model` defines the core
data types.

## Requirements

- JDK 8 or later
- The bundled `sqlite-jdbc-3.53.4.0.jar` driver (included in the project directory)

## Running the app

From the `PERSONAL_TASK_MANAGER` directory, compile and run using the SQLite
JDBC driver on the classpath (see `Command-line_commands.txt`):

```bash
javac -cp ".;C:\VSCODE_PROJECTS\PERSONAL_TASK_MANAGER\sqlite-jdbc-3.53.4.0.jar" App.java model\*.java database\*.java storage\*.java services\*.java ui\*.java
java --enable-native-access=ALL-UNNAMED -cp ".;C:\VSCODE_PROJECTS\PERSONAL_TASK_MANAGER\sqlite-jdbc-3.53.4.0.jar" PERSONAL_TASK_MANAGER.App
```

On first run, `DatabaseConnection` creates `taskmanager.db` and the `tasks`
table automatically if they don't already exist.

## Usage

Once running, choose an option from the menu:

```
===============  The tasks management system ===============
1.Insert new task
2.Show all task
3.Mark task as completed
4.Delete task
5.Filter task based on priority
6.Filter task based on category
7.Show tasks sorted by due date
0.Exit
=============================================================
```

Follow the prompts to enter task details. Dates use the `YYYY-MM-DD` format
and can be left blank if a task has no due date.

## Notes

- Data is persisted to the SQLite database `taskmanager.db`, so tasks survive
  program restarts.
- Task IDs are assigned automatically by SQLite (`AUTOINCREMENT`) and increment
  for each new task.
