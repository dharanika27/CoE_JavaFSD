package Week1;

import java.util.Scanner;

public class Task3 {
    public static void processInput() {
        Scanner s = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter a number: ");
                double number = s.nextDouble(); // Read user input

                // Check for division by zero
                if (number == 0) {
                    throw new ArithmeticException("Reciprocal of zero is undefined.");
                }

                double reciprocal = 1 / number; // Calculate reciprocal
                System.out.println("Reciprocal: " + reciprocal);
                break; // Exit the loop after successful input

            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Invalid input! Please enter a valid number.");
                s.next(); // Clear invalid input
            }
        }

        s.close();
    }

    public static void main(String[] args) {
        processInput(); // Call the method to handle input
    }
}
