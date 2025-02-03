package Week1;

import java.util.*;

class Task implements Comparable<Task> {
    private String id;
    private String description;
    private int priority;

    public Task(String id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority); // Higher priority first
    }

    @Override
    public String toString() {
        return "Task[ID=" + id + ", Description=" + description + ", Priority=" + priority + "]";
    }
}

class TaskManager {
    private PriorityQueue<Task> taskQueue;
    private Map<String, Task> taskMap;

    public TaskManager() {
        taskQueue = new PriorityQueue<>();
        taskMap = new HashMap<>();
    }

    public void addTask(String id, String description, int priority) {
        if (taskMap.containsKey(id)) {
            System.out.println("Task ID must be unique.");
            return;
        }
        Task task = new Task(id, description, priority);
        taskQueue.add(task);
        taskMap.put(id, task);
    }

    public boolean removeTask(String id) {
        Task task = taskMap.remove(id);
        if (task != null) {
            taskQueue.remove(task);
            return true;
        }
        return false;
    }

    public Task getHighestPriorityTask() {
        return taskQueue.peek();
    }

    public void displayTasks() {
        System.out.println("Tasks in queue:");
        for (Task task : taskQueue) {
            System.out.println(task);
        }
    }
}

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Get Highest Priority Task");
            System.out.println("4. Display Tasks");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Task ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Task Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Task Priority (Higher value = Higher priority): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    manager.addTask(id, description, priority);
                    break;

                case 2:
                    System.out.print("Enter Task ID to remove: ");
                    String removeId = scanner.nextLine();
                    if (manager.removeTask(removeId)) {
                        System.out.println("Task removed successfully.");
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;

                case 3:
                    Task highestPriorityTask = manager.getHighestPriorityTask();
                    if (highestPriorityTask != null) {
                        System.out.println("Highest Priority Task: " + highestPriorityTask);
                    } else {
                        System.out.println("No tasks available.");
                    }
                    break;

                case 4:
                    manager.displayTasks();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
