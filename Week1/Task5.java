
package Week1;

import java.util.Scanner;

public class Task5 {

    public static String reverseString(String str) {
        String rev = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            rev += str.charAt(i);
        }
        return rev;
    }

    public static int countOccurrences(String str, String substr) {
        int count = 0;
        int idx = str.indexOf(substr);

        while (idx != -1) {
            count++;
            idx = str.indexOf(substr, idx + substr.length());
        }

        return count;
    }

    public static String splitAndCapitalize(String str) {
        if (str.trim().isEmpty()) {
            return "No words!";
        }

        String[] words = str.trim().split(" ");
        String result = "";

        for (String word : words) {
            if (!word.isEmpty()) {
                result += Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase() + " ";
            }
        }

        return result.trim();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String str = s.nextLine().trim();

        System.out.print("Enter a substring to count the occurrences: ");
        String substr = s.nextLine();

        System.out.println("======================");
        System.out.println("Reversed String: " + reverseString(str));
        System.out.println("Occurrences of " + substr + " : " + countOccurrences(str, substr));
        System.out.println("Capitalized String: " + splitAndCapitalize(str));

        s.close();
    }
}
