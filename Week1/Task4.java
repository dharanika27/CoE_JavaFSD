package Week1;

import java.io.*;
import java.util.*;

class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "," + email;
    }

    public static User fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length == 2) {
            return new User(parts[0], parts[1]);
        }
        return null;
    }
}

class UserManager {
    List<User> users = new ArrayList<>();

    public void addUser(String name, String email) {
        users.add(new User(name, email));
        System.out.println("User added: " + name + " (" + email + ")");
    }

    public void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
            System.out.println("User data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void loadUsersFromFile(String filename) {
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) {
                    users.add(user);
                }
            }
            System.out.println("User data loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public void displayUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("User List:");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }
}

public class Task4 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        UserManager userManager = new UserManager();
        String filename = "users.txt";

        while (true) {
            System.out.println("\n1. Add User");
            System.out.println("2. Save Users to File");
            System.out.println("3. Load Users from File");
            System.out.println("4. Display Users");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = s.nextInt();
            s.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = s.nextLine();
                    System.out.print("Enter Email: ");
                    String email = s.nextLine();
                    userManager.addUser(name, email);
                    break;
                case 2:
                    userManager.saveUsersToFile(filename);
                    break;
                case 3:
                    userManager.loadUsersFromFile(filename);
                    break;
                case 4:
                    userManager.displayUsers();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    s.close();
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
